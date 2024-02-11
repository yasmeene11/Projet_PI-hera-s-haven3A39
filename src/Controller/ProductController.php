<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProductController extends AbstractController
{
    #[Route('/List_p', name: 'app_listP')]
    public function ListP(): Response
    {
        return $this->render('/Back/Product/ListP.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_p', name: 'app_add_P')]
    public function AddP(): Response
    {
        return $this->render('/Back/Product/AddP.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
}
