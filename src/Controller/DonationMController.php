<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DonationMController extends AbstractController
{
    #[Route('/List_d', name: 'app_listD')]
    public function ListD(): Response
    {
        return $this->render('/Back/Donation/ListD.html.twig', [
            'controller_name' => 'DonationMController',
        ]);
    }
    #[Route('/add_dm', name: 'app_add_Dm')]
    public function AddDm(): Response
    {
        return $this->render('/Back/Donation/AddDm.html.twig', [
            'controller_name' => 'DonationMController',
        ]);
    }
    #[Route('/List_dmf', name: 'app_listDmF')]
    public function ListDmF(): Response
    {
        return $this->render('/Front/Donation/ListDm.html.twig', []);
    }
    
}
