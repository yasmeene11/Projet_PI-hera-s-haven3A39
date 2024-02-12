<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AppointmentController extends AbstractController
{
    #[Route('/List_ap', name: 'app_listAp')]
    public function ListAp(): Response
    {
        return $this->render('/Back/Appointment/ListAp.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    #[Route('/add_ap', name: 'app_add_Ap')]
    public function AddAp(): Response
    {
        return $this->render('/Back/Appointment/AddAp.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }

    #[Route('/List_apf', name: 'app_listApF')]
    public function ListApF(): Response
    {
        return $this->render('/Front/Appointment/ListAp.html.twig', []);
    }
    
}
