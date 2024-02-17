<?php

namespace App\Controller;

use App\Entity\DonationM;
use App\Entity\Account;

use App\Repository\DonationMRepository;
use App\Repository\DonationPRepository;
use App\Form\DonationMFrontType;

use App\Form\DonationMType;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class DonationMController extends AbstractController
{
    #[Route('/List_d', name: 'app_listD')]
    public function ListD(DonationMRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/DonationM/ListD.html.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/donation', name: 'donation')]
    public function List(): Response
    {
    
        return $this->render('/Back/donation.html.twig');
    }
    #[Route('/add_donationM', name: 'app_addDonationM')]
    public function addDonationM(ManagerRegistry $mr, Request $req): Response
    {
        $s = new DonationM(); 

        $form = $this->createForm(DonationMType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()&& $form->isValid()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listD');
        }

        return $this->render('Back/DonationM/addDonationM.twig', [
            'formDonationM' => $form->createView(),
        ]);
    }
    #[Route('/delete_donationM/{donationMId}', name: 'app_delete_donationM')]
     public function removeD(DonationMRepository $repo, $donationMId, ManagerRegistry $mr): Response
    {
    $donationM = $repo->find($donationMId); 
    $em = $mr->getManager();
    $em->remove($donationM);
    $em->flush();
    
   
    return $this-> redirectToRoute('app_listD');
    }
    #[Route('/update_donationM/{donationMId}', name: 'app_update_donationM')]
    public function updateDonationM(ManagerRegistry $mr, Request $req, $donationMId): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(DonationM::class)->find($donationMId);

        $form = $this->createForm(DonationMType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('app_listD');
        }

        return $this->render('/Back/DonationM/addDonationM.twig', [
            'formDonationM' => $form->createView(),
            'donationMId' => $donationMId,
        ]);
    }
 
    #[Route('/donationMFront', name: 'donationMFront')]
public function addFront(Request $request, ManagerRegistry $managerRegistry): Response
{
    try {
        // Obtenez l'Account d'id 1
        $entityManager = $this->getDoctrine()->getManager();
        $account = $entityManager->getRepository(Account::class)->find(1);

        // Créez une nouvelle instance de DonationM avec l'Account prérempli
        $donationM = new DonationM();
        $donationM->setAccountKey($account);
        // Créez le formulaire avec l'instance de DonationM préremplie
        $form = $this->createForm(DonationMFrontType::class, $donationM);
        

        $form->handleRequest($request);
//dd($form->isValid());
        if ($form->isSubmitted() && $form->isValid()) {
            // Enregistrez l'entité dans la base de données
            $entityManager->persist($donationM);
            $entityManager->flush();
            //dd( $donationM);


            return $this->redirectToRoute('home');
        }
    } catch (\Exception $e) {
        // Log the exception or print it for debugging
        dd($e->getMessage());
    }

    return $this->render('/Front/DonationM/addDonationM.html.twig', [
        'formDonationM' => $form->createView(),
    ]);
}


}
