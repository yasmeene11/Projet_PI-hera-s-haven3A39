<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DonationPController extends AbstractController
{
    #[Route('/List_d', name: 'app_listD')]
    public function ListD(): Response
    {
        return $this->render('/Back/Donation/ListD.html.twig', [
            'controller_name' => 'DonationPController',
        ]);
    }
    #[Route('/add_dp', name: 'app_add_Dp')]
    public function AddDp(): Response
    {
        return $this->render('/Back/Donation/AddDp.html.twig', [
            'controller_name' => 'DonationPController',
        ]);
    }
    #[Route('/List_dpf', name: 'app_listDpF')]
    public function ListDpF(): Response
    {
        return $this->render('/Front/Donation/ListDp.html.twig', []);
    }
    
}
