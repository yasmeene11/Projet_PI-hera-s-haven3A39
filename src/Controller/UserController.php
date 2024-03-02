<?php

namespace App\Controller;

use App\Entity\Account;
use App\Form\RegisterType;
use App\Repository\AccountRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Mime\Address;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
use Symfony\Component\Asset\Packages;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;

use Symfony\Component\HttpFoundation\Session\SessionInterface;

use Symfony\Component\Form\Extension\Core\Type\EmailType; // Added
class UserController extends AbstractController
{
    #[Route('/ListUsers', name: 'app_listU')]
    public function ListU(AccountRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/User/ListU.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/LoginBack', name: 'logino')]
    public function Listo(): Response
    {
       
        return $this->render('Back/User/LoginAdBack.html.twig', [
            
        ]);
    }


    #[Route('/update_u/{UserId}', name: 'app_update_U')]
public function updateU(ManagerRegistry $mr, Request $req, $UserId): Response
{
    $em = $mr->getManager();
    $user = $em->getRepository(Account::class)->find($UserId); // Update entity class to your User entity

    $form = $this->createForm(RegisterType::class, $user, [
        'include_password_field' => false, // Pass an option to exclude the password field from the form
        'is_update_form' => true, // Indicate that this is an update form
    ]); 

    $form->handleRequest($req);

    if ($form->isSubmitted() && $form->isValid()) {
        $em->flush();

        return $this->redirectToRoute('app_listU');
    }

    return $this->render('/Back/User/UpdateAd.html.twig', [
        'form' => $form->createView(),
        'userId' => $UserId,
    ]);
}

    
    
    #[Route('/user_add', name: 'app_add_U')]
    public function AddU(): Response
    {
        return $this->render('/Back/User/AddU.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }

    #[Route('/delete_User/{UserId}', name: 'app_delete_U')]
    public function removeA(AccountRepository $repo, $UserId, ManagerRegistry $mr): Response
    {
        $User = $repo->find($UserId);
        $em = $mr->getManager();
        $em->remove($User);
        $em->flush();

        return $this->redirectToRoute('app_listU');
    }

    #[Route('/RegisterBack', name: 'app_Register_B')]
    public function register(Request $request, UserPasswordEncoderInterface $passwordEncoder): Response
    {
        $errorMessage = '';

        // Create a new instance of Account entity
        $user = new Account();

        // Create the form
        $form = $this->createForm(RegisterType::class, $user);

        // Handle form submission
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            // Check if the user already exists with the same email
            $existingUser = $this->getDoctrine()->getRepository(Account::class)->findOneBy(['Email' => $user->getEmail()]);
            if ($existingUser) {
                $errorMessage = 'User with this email already exists.';
            } else {
                // Encode the password
                $encodedPassword = $passwordEncoder->encodePassword($user, $user->getPassword());
                $user->setPassword($encodedPassword);
                $user->setAccountStatus("Pending");

                // Save the user
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($user);
                $entityManager->flush();

                // Redirect to appropriate page
                $this->addFlash('success', 'Registration successful.');
                return $this->redirectToRoute('app_listU');
            }
        }

        // Render the Twig template with the form
        return $this->render('Back/User/RegisterAdBack.html.twig', [
            'form' => $form->createView(),
            'errorMessage' => $errorMessage,
        ]);
    }

#[Route('/LoginBack_C', name: 'app_Login_B')]
public function login(Request $request, UserPasswordEncoderInterface $passwordEncoder): Response
{
    // Handle form submission
    if ($request->isMethod('POST')) {
        // Retrieve submitted data
        $emailOrUsername = $request->request->get('emailOrUsername');
        $password = $request->request->get('password');

        // Retrieve user by email or username
        $user = $this->getDoctrine()->getRepository(Account::class)->findOneBy(['Email' => $emailOrUsername]);

        if (!$user) {
            // User not found
            $this->addFlash('error', 'Invalid credentials.');
            return $this->redirectToRoute('app_Login_B');
        }

        // Check if the provided password is correct
        if ($passwordEncoder->isPasswordValid($user, $password)) {
            // Password is correct, login successful
            // You can implement your login logic here, such as setting session variables, etc.
            $this->addFlash('success', 'Login successful.');
            return $this->redirectToRoute('app_listU');
        }

        // Invalid password
        $this->addFlash('error', 'Invalid credentials.');
        return $this->redirectToRoute('app_Login_B');
    }

    // Render the login form
    return $this->render('/Back/User/LoginAdBack.html.twig');
}


#[Route('/LoginFront_C', name: 'app_Login_F')]
public function loginFront(Request $request, UserPasswordEncoderInterface $passwordEncoder, SessionInterface $session): Response
{
    // Handle form submission
    if ($request->isMethod('POST')) {
        // Retrieve submitted data
        $emailOrUsername = $request->request->get('emailOrUsername');
        $password = $request->request->get('password');

        // Retrieve user by email or username
        $user = $this->getDoctrine()->getRepository(Account::class)->findOneBy(['Email' => $emailOrUsername]);

        if (!$user) {
            // User not found
            $this->addFlash('error', 'User does not exist.');
            return $this->redirectToRoute('app_Login_F');
        } elseif (!$passwordEncoder->isPasswordValid($user, $password)) {
            // Invalid password
            $this->addFlash('error', 'Incorrect password.');
            return $this->redirectToRoute('app_Login_F');
        } elseif ($user->getRole() == 'admin' || $user->getAccountStatus() !== 'active') {
            // Account status is not 'active' or role is not 'user'
            $this->addFlash('error', 'Your account is not active.');
            return $this->redirectToRoute('app_Login_F');
        } else {

            // Password is correct, login successful
            // Start a session and store the user ID
            $session->set('user_id', $user->getaccountId());
        
            $this->addFlash('success', 'Login successful.');
            
                return $this->redirectToRoute('testuser');
           
        }
    }
  
    // Render the login form
    return $this->render('/index_login/Login.html.twig');
}

#[Route('/RegisterFront', name: 'app_Register_F')]
public function registerPage(?string $errorMessage): Response
{
    return $this->render('/index_login/register.html.twig', [
        'errorMessage' => $errorMessage,
    ]);
}

    #[Route('/registerb', name: 'register_submit', methods: ['POST'])]
    public function registerSubmit(Request $request, UserPasswordEncoderInterface $passwordEncoder, SessionInterface $session): Response
    {
        $errorMessage = '';
    
        // Retrieve submitted data
        $name = $request->request->get('name');
        $surname = $request->request->get('surname');
        $email = $request->request->get('email');
        $password = $request->request->get('password');
        $phoneNumber = $request->request->get('phone_number');
        $address = $request->request->get('address');
        $gender = $request->request->get('gender');
        $recaptchaResponse = $request->request->get('g-recaptcha-response');
    
        // Validate reCAPTCHA
        $isValidRecaptcha = $this->validateRecaptcha($recaptchaResponse);
    
        if (!$isValidRecaptcha) {
            $errorMessage = 'Please complete the reCAPTCHA.';
        } else {
            // Proceed with form validation and registration logic
            // Check if any required field is empty
            if (empty($name) || empty($surname) || empty($email) || empty($password) || empty($phoneNumber) || empty($address) || empty($gender)) {
                $errorMessage = 'Please fill in all the required fields.';
            }
    
            // Check if email is valid
            if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                $errorMessage = 'Please enter a valid email address.';
            }
    
            // Check if the user already exists with the same email
            $existingUser = $this->getDoctrine()->getRepository(Account::class)->findOneBy(['Email' => $email]);
            if ($existingUser) {
                $errorMessage = 'User already exists.';
            }
    
            // Encode the password and save the user
            if (empty($errorMessage)) {
                $user = new Account();
                $encodedPassword = $passwordEncoder->encodePassword($user, $password);
                $user->setAccountStatus("active");
                $user->setRole("user");
                $user->setName($name);
                $user->setSurname($surname);
                $user->setEmail($email);
                $user->setPassword($encodedPassword);
                $user->setPhoneNumber($phoneNumber);
                $user->setAddress($address);
                $user->setGender($gender);
    
                // Save the user
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($user);
                $entityManager->flush();
    
                // Redirect or display success message
                $session->set('user_id', $user->getaccountId());
                $this->addFlash('success', 'Registration successful.');
                return $this->redirectToRoute('testuser');
            }
        }
    
        // Render the Twig template with error message
        return $this->render('/index_login/register.html.twig', [
            'errorMessage' => $errorMessage,
        ]);
    }
    

    private function validateRecaptcha($recaptchaResponse)
{
    $url = 'https://www.google.com/recaptcha/api/siteverify';
    $secret = '6LfGv4ApAAAAAOVyYesOfQOZKKntP4dIrFWyF0au'; // Replace with your actual secret key

    $data = [
        'secret' => $secret,
        'response' => $recaptchaResponse,
    ];

    $options = [
        'http' => [
            'header' => "Content-type: application/x-www-form-urlencoded\r\n",
            'method' => 'POST',
            'content' => http_build_query($data),
        ],
    ];

    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    if ($result === false) {
        return false; // Error fetching reCAPTCHA response
    }

    $response = json_decode($result, true);

    return $response['success'] ?? false;
}



//////////////test

#[Route('/testuser', name: 'testuser')]
public function testUser(SessionInterface $session): Response
{
    // Retrieve the user's ID from the session
    $userId = $session->get('user_id');

    // Retrieve the user entity from the database using the ID
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);

    // Check if user exists
    if (!$user) {
        throw $this->createNotFoundException('User not found.');
    }

    // Pass the user data to the Twig template
    return $this->render('/index_Login/testuserfront.html.twig', [
        'user' => $user,
    ]);
}


#[Route('/logout', name: 'logout')]
    public function logout(SessionInterface $session): Response
    {
        // Invalidate the session to log the user out
        $session->invalidate();

        // Redirect the user to the login page or any other page
        return $this->redirectToRoute('app_Login_F');
    }



    #[Route('/update_uf{UserId}', name: 'edit_user_f')]
public function updateUf(ManagerRegistry $mr, Request $req, $UserId): Response
{
    $em = $mr->getManager();
    $user = $em->getRepository(Account::class)->find($UserId); // Update entity class to your User entity

    $form = $this->createForm(RegisterType::class, $user, [
        'include_password_field' => false, // Pass an option to exclude the password field from the form
        'include_role_field' => false, // Exclude the role field
        'include_account_status_field' => false, // Exclude the account status field
        'is_update_form' => true, // Indicate that this is an update form
    ]); 

    $form->handleRequest($req);

    if ($form->isSubmitted() && $form->isValid()) {
        $em->flush();

        return $this->redirectToRoute('testuser');
    }

    return $this->render('/index_Login/updateloginuser.html.twig', [
        'form' => $form->createView(),
        'userId' => $UserId,
    ]);
}


#[Route('/delete_User/{UserId}', name: 'app_delete_UF')]
public function removeAf(AccountRepository $repo, $UserId, ManagerRegistry $mr, SessionInterface $session): Response
{
    $User = $repo->find($UserId);
    $em = $mr->getManager();
    $em->remove($User);
    $em->flush();

    // Close the session
    $session->invalidate();

    return $this->redirectToRoute('app_Login_F');
}

/////////////////password 



#[Route('/forgot-password', name: 'app_forgot_password')]
public function forgotPassword(Request $request, AccountRepository $userRepository, Packages $assetPackages): Response
{
    $form = $this->createFormBuilder()
        ->add('email', EmailType::class)
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $email = $form->get('email')->getData();
        $user = $userRepository->findOneBy(['Email' => $email]);

        if ($user) {
            $token = md5(uniqid());
            $user->setResetToken($token);
            $user->setResetTokenRequestedAt(new \DateTimeImmutable());
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            $resetLink = $this->generateUrl('app_reset_password', ['token' => $token], UrlGeneratorInterface::ABSOLUTE_URL);

            // Read images as base64
            $imageHeader = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/header3.png');
            $imageHeaderBase64 = base64_encode($imageHeader);
            
            $imageLogo = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/Design_sans_titre_4.png');
            $imageLogoBase64 = base64_encode($imageLogo);
            
            $imageBeefree = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/background_2.png');
            $imageBeefreeBase64 = base64_encode($imageBeefree);
            
           
            
            $imageUnitedPets = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/Design_sans_titre_4.png');
            $imageUnitedPetsBase64 = base64_encode($imageUnitedPets);
            
            $imageFacebook = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/facebook2x.png');
            $imageFacebookBase64 = base64_encode($imageFacebook);
            
            $imageInstagram = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/instagram2x.png');
            $imageInstagramBase64 = base64_encode($imageInstagram);
            
            $imageTwitter = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/twitter2x.png');
            $imageTwitterBase64 = base64_encode($imageTwitter);

            $backgroundImage = file_get_contents($this->getParameter('kernel.project_dir').'/public/images/background_2.png');
$backgroundImageBase64 = base64_encode($backgroundImage);

            // Send email
            $mail = new PHPMailer(true);
            try {
                $mail->isSMTP();
                $mail->Host = 'smtp.gmail.com';
                $mail->Port = 587;
                $mail->SMTPSecure = 'tls';
                $mail->SMTPAuth = true;
                $mail->Username = 'adembg0@gmail.com';
                $mail->Password = 'msul wxkj qdws hfna';
                $mail->setFrom('UnitedPets2024@gmail.com', 'United Pets');
                $mail->addAddress($user->getEmail(), $user->getName());
                $mail->Subject = 'Password Reset';
                $mail->msgHTML(
                    $this->renderView(
                        'index_login/forgot_password.html.twig',
                        [
                            'resetLink' => $resetLink,
                            'imageHeader' => $imageHeaderBase64,
                            'imageLogo' => $imageLogoBase64,
                            'imageBeefree' => $imageBeefreeBase64,
                            'imageFacebook' => $imageFacebookBase64,
                            'imageInstagram' => $imageInstagramBase64,
                            'imageTwitter' => $imageTwitterBase64,
                            'backgroundImage' => $backgroundImageBase64,
                        ]
                    )
                );

                $mail->send();
                $this->addFlash('success', 'Check your email for the password reset link.');
            } catch (Exception $e) {
                $this->addFlash('error', 'An error occurred while sending the email.');
            }
        } else {
            $this->addFlash('error', 'No user found with that email address.');
        }
    }

    return $this->render('index_Login/resetpasswordinterface.html.twig', [
        'form' => $form->createView(),
    ]);
}

#[Route('/reset-password/{token}', name: 'app_reset_password')]
public function resetPassword(Request $request, string $token, AccountRepository $userRepository, UserPasswordEncoderInterface $passwordEncoder): Response
{
    $user = $userRepository->findOneBy(['resetToken' => $token]);

    if (!$user || $user->getResetTokenRequestedAt()->diff(new \DateTimeImmutable())->h > 2) {
        $this->addFlash('error', 'Invalid or expired reset token.');
        return $this->redirectToRoute('app_forgot_password');
    }

    $form = $this->createFormBuilder()
        ->add('password', RepeatedType::class, [
            'type' => PasswordType::class,
            'invalid_message' => 'The password fields must match.',
            'first_options' => ['label' => 'New password'],
            'second_options' => ['label' => 'Repeat new password'],
        ])
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $password = $form->get('password')->getData();

        // Encode and set the new password
        $user->setPassword($passwordEncoder->encodePassword($user, $password));
        $user->setResetToken(null);
        $user->setResetTokenRequestedAt(null);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Your password has been reset successfully.');

        return $this->redirectToRoute('app_forgot_password');
    }

    return $this->render('index_Login/reset_password.html.twig', [
        'form' => $form->createView(),
    ]);
}


  
    

}