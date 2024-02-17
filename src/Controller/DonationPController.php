<?php

namespace App\Controller;
use App\Entity\DonationP;
use App\Entity\Account;

use App\Repository\DonationPRepository;
use App\Form\DonationPType;
use App\Form\DonationPFrontType;

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
    #[Route('/donationPFront', name: 'donationPFront')]
public function addFront(Request $request, ManagerRegistry $managerRegistry): Response
{
    try {
        // Obtenez l'Account d'id 1
        $entityManager = $this->getDoctrine()->getManager();
        $account = $entityManager->getRepository(Account::class)->find(1);

        // Créez une nouvelle instance de DonationM avec l'Account prérempli
        $donationP = new DonationP();
        $donationP->setAccountKey($account);
        // Créez le formulaire avec l'instance de DonationM préremplie
        $form = $this->createForm(DonationPFrontType::class, $donationP);
        

        $form->handleRequest($request);
//dd($form->isValid());
        if ($form->isSubmitted() && $form->isValid()) {
            // Enregistrez l'entité dans la base de données
            $entityManager->persist($donationP);
            $entityManager->flush();
            //dd( $donationM);


            return $this->redirectToRoute('home');
        }
    } catch (\Exception $e) {
        // Log the exception or print it for debugging
        dd($e->getMessage());
    }

    return $this->render('/Front/DonationP/addDonationP.html.twig', [
        'formDonationP' => $form->createView(),
    ]);
}

}
