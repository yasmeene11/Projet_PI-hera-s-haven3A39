<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ReportController extends AbstractController
{
    #[Route('/List_r', name: 'app_listR')]
    public function ListR(): Response
    {
        return $this->render('/Back/Appointment/ListR.html.twig', [
            'controller_name' => 'ReportController',
        ]);
    }
    #[Route('/add_r', name: 'app_add_R')]
    public function AddR(): Response
    {
        return $this->render('/Back/Appointment/AddR.html.twig', [
            'controller_name' => 'ReportController',
        ]);
    }

    #[Route('/List_rf', name: 'app_listRF')]
    public function ListRF(): Response
    {
        return $this->render('/Front/Appointment/ListR.html.twig', []);
    }

    
}
