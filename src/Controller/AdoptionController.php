<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AdoptionController extends AbstractController
{
    #[Route('/List_ad', name: 'app_listAd')]
    public function ListAd(): Response
    {
        return $this->render('/Back/Animal/ListAd.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_ad', name: 'app_add_Ad')]
    public function AddAd(): Response
    {
        return $this->render('/Back/Animal/AddAd.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
}