<?php

namespace App\Controller;
use App\Entity\DonationP;
use App\Repository\DonationPRepository;
use App\Form\DonationPType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Doctrine\Persistence\ManagerRegistry;

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
    #[Route('/list_donationP', name: 'app_list_donationP')]
    public function ListDP(DonationPRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/DonationP/list_donationP.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/add_donationP', name: 'app_addDonationP')]
    public function addDonationP(ManagerRegistry $mr, Request $req): Response
    {
        $s = new DonationP(); 

        $form = $this->createForm(DonationPType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()&& $form->isValid()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_list_donationP');
        }

        return $this->render('Back/DonationP/addDonationP.twig', [
            'formDonationP' => $form->createView(),
        ]);
    }
    #[Route('/delete_donationP/{donationPId}', name: 'app_delete_donationP')]
     public function removeDonationP(DonationPRepository $repo, $donationPId, ManagerRegistry $mr): Response
    {
    $donationP = $repo->find($donationPId); 
    $em = $mr->getManager();
    $em->remove($donationP);
    $em->flush();
    
   
    return $this-> redirectToRoute('app_list_donationP');
    }
    #[Route('/update_donationP/{donationPId}', name: 'app_update_donationP')]
    public function updateDonationP(ManagerRegistry $mr, Request $req, $donationPId): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(DonationP::class)->find($donationPId);

        $form = $this->createForm(DonationPType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('app_list_donationP');
        }

        return $this->render('/Back/DonationP/addDonationP.twig', [
            'formDonationP' => $form->createView(),
            'donationPId' => $donationPId,
        ]);
    }
}
