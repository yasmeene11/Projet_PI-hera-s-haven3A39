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
    #[Route('/List_u', name: 'app_listU')]
    public function ListU(AccountRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/User/ListU.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/logino', name: 'logino')]
    public function Listo(AccountRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('Back/User/LoginAdBack.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/user_add', name: 'app_add_U')]
    public function AddU(): Response
    {
        return $this->render('/Back/User/AddU.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }

    #[Route('/delete_a/{UserId}', name: 'app_delete_U')]
    public function removeA(AccountRepository $repo, $UserId, ManagerRegistry $mr): Response
    {
        $User = $repo->find($UserId);
        $em = $mr->getManager();
        $em->remove($User);
        $em->flush();

        return $this->redirectToRoute('app_listU');
    }

    #[Route('/RegisterBack', name: 'app_Register_B')]
public function AddA(ManagerRegistry $mr, Request $req, UserPasswordEncoderInterface $passwordEncoder): Response
{
    $user = new Account();  // Instantiate User entity

    $form = $this->createForm(RegisterType::class, $user);

    $form->handleRequest($req);
    if ($form->isSubmitted() && $form->isValid()) {
        // Encode the password
        $encodedPassword = $passwordEncoder->encodePassword($user, $user->getPassword());
        $user->setPassword($encodedPassword);

        // Save the user
        $em = $mr->getManager();
        $em->persist($user);
        $em->flush();

        return $this->redirectToRoute('app_listU');
    }

    return $this->render('/Back/User/RegisterAdBack.html.twig', [
        'form' => $form->createView(),
    ]);
}

#[Route('/LoginBack', name: 'app_Login_B')]
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
