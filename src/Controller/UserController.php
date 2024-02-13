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


    #[Route('/UpdateBack/{UserId}', name: 'app_update_B')]
    public function Listoo(AccountRepository $accountRepository, $UserId): Response
    {
        // Find the user by UserId
        $user = $accountRepository->find($UserId);
    
        // Check if user exists
        if (!$user) {
            // Handle case when user is not found, e.g., redirect or display an error message
            // For example:
            throw $this->createNotFoundException('User not found');
        }
    
        return $this->render('Back/User/UpdateAd.html.twig', [
            'user' => $user,
        ]);
    }

    #[Route('/UpdateBackConfirm/{UserId}', name: 'app_update_CB')]
public function update(Request $request, UserPasswordEncoderInterface $passwordEncoder, int $UserId): Response
{
    // Get the user by its ID
    $entityManager = $this->getDoctrine()->getManager();
    $user = $entityManager->getRepository(Account::class)->find($UserId);

    if (!$user) {
        throw $this->createNotFoundException('User not found');
    }

    // Update user details based on the submitted form data
    $user->setName($request->request->get('name'));
    $user->setSurname($request->request->get('surname'));
    $user->setGender($request->request->get('gender'));
    $user->setPhoneNumber($request->request->get('phone_number'));
    $user->setAddress($request->request->get('address'));
    $user->setEmail($request->request->get('email'));
    $user->setRole($request->request->get('role'));

    // Save the updated user
    $entityManager->flush();

    // Redirect to appropriate page
    $this->addFlash('success', 'User details updated successfully.');
    return $this->redirectToRoute('app_listU');
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

    if ($request->isMethod('POST')) {
        $name = $request->request->get('name');
        $surname = $request->request->get('surname');
        $gender = $request->request->get('gender');
        $phoneNumber = $request->request->get('phone_number');
        $address = $request->request->get('address');
        $email = $request->request->get('email');
        $password = $request->request->get('password');
        $role = $request->request->get('role');

        // Perform input validation here

        // Check if the user already exists with the same email
        $existingUser = $this->getDoctrine()->getRepository(Account::class)->findOneBy(['Email' => $email]);
        if ($existingUser) {
            $errorMessage = 'User with this email already exists.';
        } else {
            // Create a new user
            $user = new Account();
            $user->setName($name);
            $user->setSurname($surname);
            $user->setGender($gender);
            $user->setPhoneNumber($phoneNumber);
            $user->setAddress($address);
            $user->setEmail($email);
            $user->setRole($role);
            $user->setAccountStatus("Pending");

            // Encode the password
            $encodedPassword = $passwordEncoder->encodePassword($user, $password);
            $user->setPassword($encodedPassword);

            // Save the user
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            // Redirect to appropriate page
            $this->addFlash('success', 'Registration successful.');
            return $this->redirectToRoute('app_listU');
        }
    }

    return $this->render('/Back/User/RegisterAdBack.html.twig', [
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

}
