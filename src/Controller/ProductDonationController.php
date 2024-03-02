<?php

namespace App\Controller;

use App\Entity\DonationProduct;
use App\Form\FormDonationProductType;
use App\Repository\DonationProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
class ProductDonationController extends AbstractController
{
    #[Route('/List_pd', name: 'app_listpd')]
    public function ListP(DonationProductRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/ProductDonation/Listpd.html.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/add_pd', name: 'app_add_PD')]
    public function AddPD(ManagerRegistry $mr,Request $req): Response
    {
        $s=new DonationProduct();
        $form=$this->createForm(FormDonationProductType::class,$s);
        $form->handleRequest($req);
        if($form->isSubmitted()&& ($form->isValid())){
         $em=$mr->getManager();
         $em->persist($s);
         $em->flush();
         return $this->redirectToRoute('app_listpd');
        }
        return $this->render('/Back/ProductDonation/addPD.html.twig', [
            'formDonationProduct'=>$form->createView()
        ]);
    }
}