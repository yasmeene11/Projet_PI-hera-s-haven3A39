<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CategoryController extends AbstractController
{
    #[Route('/List_c', name: 'app_listC')]
    public function ListC(): Response
    {
        return $this->render('/Back/Product/ListC.html.twig', [
            'controller_name' => 'CategoryController',
        ]);
    }
    #[Route('/add_c', name: 'app_add_C')]
    public function AddP(): Response
    {
        return $this->render('/Back/Product/AddC.html.twig', [
            'controller_name' => 'CategoryController',
        ]);
    }
    
}
