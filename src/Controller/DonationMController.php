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
use Doctrine\ORM\EntityManagerInterface;



class DonationMController extends AbstractController
{
    #[Route('/List_d', name: 'app_listD')]
    public function ListD(DonationMRepository $repo, Request $request): Response
    {
        // Récupérer le champ par lequel trier depuis la requête, avec 'donationM_Date' comme valeur par défaut
        $sortField = $request->query->get('sort', 'donationM_Date'); 
    
        // Assurer que le champ de tri est un des champs valides, sinon utiliser 'donationM_Date' comme champ par défaut
        if (!in_array($sortField, ['donationM_Date', 'Donation_Amount'])) {
            $sortField = 'donationM_Date';
        }
    
        // Récupérer la direction du tri depuis la requête, avec 'asc' comme valeur par défaut
        $sortDirection = $request->query->get('direction', 'asc');
        
        // Valider la direction du tri
        $sortDirection = $sortDirection === 'desc' ? 'DESC' : 'ASC';
    
        // Modifier le champ de tri pour correspondre exactement au nom de l'attribut dans l'entité
        $sortFieldMapping = [
            'donationM_Date' => 'donationM_Date', // Assurez-vous que c'est le nom correct de l'attribut
            'Donation_Amount' => 'Donation_Amount'
        ];
    
        $sortField = $sortFieldMapping[$sortField] ?? 'donationM_Date';
    
        // Utiliser le champ et la direction pour trier les résultats
        $result = $repo->findBy([], [$sortField => $sortDirection]);
    
        return $this->render('/Back/DonationM/ListD.html.twig', [
            'result' => $result,
        ]);
    }
    
    #[Route('/listDonationMFront', name: 'listDonationMFront')]
    public function ListDFront(DonationMRepository $repo, Request $request): Response
    {
        $sortField = $request->query->get('sort', 'donationM_Date'); 
    
        // Assurer que le champ de tri est un des champs valides, sinon utiliser 'donationM_Date' comme champ par défaut
        if (!in_array($sortField, ['donationM_Date', 'Donation_Amount'])) {
            $sortField = 'donationM_Date';
        }
    
        // Récupérer la direction du tri depuis la requête, avec 'asc' comme valeur par défaut
        $sortDirection = $request->query->get('direction', 'asc');
        
        // Valider la direction du tri
        $sortDirection = $sortDirection === 'desc' ? 'DESC' : 'ASC';
    
        // Modifier le champ de tri pour correspondre exactement au nom de l'attribut dans l'entité
        $sortFieldMapping = [
            'donationM_Date' => 'donationM_Date', // Assurez-vous que c'est le nom correct de l'attribut
            'Donation_Amount' => 'Donation_Amount'
        ];
    
        $sortField = $sortFieldMapping[$sortField] ?? 'donationM_Date';
    
        // Utiliser le champ et la direction pour trier les résultats
        $result = $repo->findBy([], [$sortField => $sortDirection]);
    
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
            
            $cashRegister->setIdEntity($s->getdonationMId());

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
    
        $entityManager = $this->getDoctrine()->getManager();
        $account = $entityManager->getRepository(Account::class)->find(1);

        $donationM = new DonationM();
        $donationM->setAccountKey($account);
        $form = $this->createForm(DonationMFrontType::class, $donationM);
        

        $form->handleRequest($request);
//dd($form->isValid());
        if ($form->isSubmitted() && $form->isValid()) {
            $donationAmount = $donationM->getDonationAmount(); 

            $entityManager->persist($donationM);
            $entityManager->flush();
            //dd( $donationM);
            $cashRegister = new CashRegister();
            $cashRegister->setType("donationM");
            $cashRegister->setInput(1);
            $cashRegister->setOutput(0);


            $cashRegister->setSomme($donationM->getDonationAmount());
            $cashRegister->setDateTransaction(new \DateTime());
            
            $cashRegister->setIdEntity($donationM->getdonationMId());

            $entityManager->persist($cashRegister);
            $entityManager->flush();

            return $this->redirectToRoute('thankYouCard', ['accountId' => $account, 
            'donationAmount' => $donationAmount]);
        }
    

    return $this->render('/Front/DonationM/addDonationM.html.twig', [
        'formDonationM' => $form->createView(),
    ]);
}
#[Route('/thankYouCard/{accountId}/{donationAmount}', name: 'thankYouCard')]

public function thankYouCard($accountId, $donationAmount,EntityManagerInterface $entityManager)
    {
        //$account = $entityManager->getRepository(Account::class)->find($accountId);
        $account=$accountId;
        return $this->render('Front/DonationM/thankYouCard.html.twig', [
            'account' => $account,
            'donationAmount' => $donationAmount,
        ]);
    }
    #[Route('/search_donationM', name: 'search_donationM')]
    public function searchDonationM(Request $request, DonationMRepository $donationMRepository): Response
    {
        $donationAmount = $request->query->get('donationAmount');
    
        $donations = $donationMRepository->findByDonationAmount($donationAmount);
    
        return $this->render('Back/DonationM/searchDonationM.html.twig', [
            'results' => $donations,
        ]);
    }
    #[Route('/search_donationMFront', name: 'search_donationMFront')]
    public function search_donationMFront(Request $request, DonationMRepository $donationMRepository): Response
    {
        $donationAmount = $request->query->get('donationAmount');
    
        $donations = $donationMRepository->findByDonationAmount($donationAmount);
    
        return $this->render('Front/DonationM/searchDonationM.html.twig', [
            'results' => $donations,
        ]);
    }

}
