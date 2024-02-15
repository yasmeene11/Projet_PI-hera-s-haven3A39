<?php

namespace App\Controller;
use App\Entity\CashRegister;
use App\Repository\CashRegisterRepository;

//use App\Form\DonationMType;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;




class CashRegisterController extends AbstractController
{
    #[Route('/cash/register', name: 'app_cash_register')]
    public function index(): Response
    {
        return $this->render('cash_register/index.html.twig', [
            'controller_name' => 'CashRegisterController',
        ]);
    }
    #[Route('/cashRegister', name: 'cashRegister')]
    public function listCashRegister(CashRegisterRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/CashRegister/listCashRegister.html.twig', [
            'result' => $result,
        ]);
    }
}
