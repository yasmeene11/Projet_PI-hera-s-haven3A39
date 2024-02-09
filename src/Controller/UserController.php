<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class UserController extends AbstractController
{
    #[Route('/List_u', name: 'app_listU')]
    public function ListU(): Response
    {
        return $this->render('/Back/User/ListU.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_u', name: 'app_add_U')]
    public function AddU(): Response
    {
        return $this->render('/Back/User/AddU.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
}
