<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class PetBoardingController extends AbstractController
{
    #[Route('/List_b', name: 'app_listB')]
    public function ListB(): Response
    {
        return $this->render('/Back/Animal/ListB.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_b', name: 'app_add_B')]
    public function AddB(): Response
    {
        return $this->render('/Back/Animal/AddB.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/List_bf', name: 'app_listBF')]
    public function ListBF(): Response
    {
        return $this->render('/Front/Animal/ListB.html.twig', []);
    }
}
