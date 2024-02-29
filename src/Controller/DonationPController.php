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
use Doctrine\ORM\EntityManagerInterface;

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
    public function ListDP(DonationPRepository $repo, Request $request): Response
    {
$sortField = $request->query->get('sort', 'donation_product_quantity'); 
    
if (!in_array($sortField, ['donation_product_quantity', 'donation_product_expiration_date'])) {
    $sortField = 'donation_product_quantity';
}

$sortDirection = $request->query->get('direction', 'asc');

$sortDirection = $sortDirection === 'desc' ? 'DESC' : 'ASC';

$sortFieldMapping = [
    'donation_product_quantity' => 'donation_product_quantity', // Assurez-vous que c'est le nom correct de l'attribut
    'donation_product_expiration_date' => 'donation_product_expiration_date'
];

$sortField = $sortFieldMapping[$sortField] ?? 'donation_product_quantity';

$result = $repo->findBy([], [$sortField => $sortDirection]); 
       return $this->render('/Back/DonationP/list_donationP.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/listDonationPFront', name: 'listDonationPFront')]
    public function ListDPFront(DonationPRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Front/DonationP/listDonationP.html.twig', [
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
    
        $entityManager = $this->getDoctrine()->getManager();
        $account = $entityManager->getRepository(Account::class)->find(1);

        $donationP = new DonationP();
        $donationP->setAccountKey($account);
        $form = $this->createForm(DonationPFrontType::class, $donationP);
        

        $form->handleRequest($request);
//dd($form->isValid());
        if ($form->isSubmitted() && $form->isValid()) {
            $productName = $donationP->getDonationProductName(); 

            $entityManager->persist($donationP);
            $entityManager->flush();
            //dd( $donationM);


            return $this->redirectToRoute('thankYouCard1', ['accountId' => $account, 
            'productName' => $productName]);        }
   

    return $this->render('/Front/DonationP/addDonationP.html.twig', [
        'formDonationP' => $form->createView(),
    ]);
}
#[Route('/thankYouCard1/{accountId}/{productName}', name: 'thankYouCard1')]

public function thankYouCard1($accountId, $productName,EntityManagerInterface $entityManager)
    {
        //$account = $entityManager->getRepository(Account::class)->find($accountId);
        $account=$accountId;
        return $this->render('Front/DonationP/thankYouCard.html.twig', [
            'account' => $account,
            'productName' => $productName,
        ]);
    }
    #[Route('/search_donationP', name: 'search_donationP')]
    public function searchDonationP(Request $request, DonationPRepository $donationPRepository): Response
    {
        $donation_product_name = $request->query->get('donation_product_name');
    
        $donations = $donationPRepository->findByProductName($donation_product_name);
    
        return $this->render('Back/DonationP/searchDonationP.html.twig', [
            'results' => $donations,
        ]);
    }
}
