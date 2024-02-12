<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DonationPController extends AbstractController
{
    #[Route('/donation/p', name: 'app_donation_p')]
    public function index(): Response
    {
        return $this->render('donation_p/index.html.twig', [
            'controller_name' => 'DonationPController',
        ]);
    }
}
