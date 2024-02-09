<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AnimalController extends AbstractController
{
    #[Route('/List_a', name: 'app_listA')]
    public function ListA(): Response
    {
        return $this->render('/Back/Animal/ListA.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_a', name: 'app_add_A')]
    public function AddA(): Response
    {
        return $this->render('/Back/Animal/AddA.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
}
