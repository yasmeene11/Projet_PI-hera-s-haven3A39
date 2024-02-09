<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DonationController extends AbstractController
{
    #[Route('/List_d', name: 'app_listD')]
    public function ListD(): Response
    {
        return $this->render('/Back/Donation/ListD.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_d', name: 'app_add_D')]
    public function AddD(): Response
    {
        return $this->render('/Back/Donation/AddD.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
}
