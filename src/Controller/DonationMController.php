<?php

namespace App\Controller;

use App\Entity\DonationM;
use App\Entity\Account;
use App\Entity\CashRegister;

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
    #[Route('/listDonationMFront', name: 'listDonationMFront')]
    public function ListDFront(DonationMRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Front/DonationM/listDonationM.html.twig', [
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
            $cashRegister = new CashRegister();
            $cashRegister->setType("donationM");
            $cashRegister->setInput(1);
            $cashRegister->setOutput(0);


            $cashRegister->setSomme($s->getDonationAmount());
            $cashRegister->setDateTransaction(new \DateTime());
            
            // Assurez-vous d'ajuster l'idEntity en fonction de vos besoins
            $cashRegister->setIdEntity($s->getdonationMId());

            // Ajouter le CashRegister à l'EntityManager
            $em->persist($cashRegister);
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
            $cashRegister = new CashRegister();
            $cashRegister->setType("donationM");
            $cashRegister->setInput(1);
            $cashRegister->setOutput(0);


            $cashRegister->setSomme($donationM->getDonationAmount());
            $cashRegister->setDateTransaction(new \DateTime());
            
            // Assurez-vous d'ajuster l'idEntity en fonction de vos besoins
            $cashRegister->setIdEntity($donationM->getdonationMId());

            // Ajouter le CashRegister à l'EntityManager
            $entityManager->persist($cashRegister);
            $entityManager->flush();

            return $this->redirectToRoute('home');
        }
    

    return $this->render('/Front/DonationM/addDonationM.html.twig', [
        'formDonationM' => $form->createView(),
    ]);
}


}
