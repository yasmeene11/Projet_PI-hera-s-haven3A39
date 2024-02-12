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
    
   
    return $this-> redirectToRoute('app_listU');
    }



    #[Route('/RegisterBack', name: 'app_Register_B')]
    public function AddA(ManagerRegistry $mr, Request $req): Response
    {
        $s = new Account();  //1 -instance

        $form = $this->createForm(RegisterType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listU');
        }

        return $this->render('/Back/User/RegisterAdBack.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    
}
