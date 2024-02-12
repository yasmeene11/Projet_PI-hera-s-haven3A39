<?php

namespace App\Controller;

use App\Entity\Adoption;
use App\Repository\AdoptionRepository;
use App\Form\AdoptionType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class AdoptionController extends AbstractController
{
    #[Route('/List_ad', name: 'app_listAd')]
    public function ListAd(AdoptionRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/Animal/ListAd.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/add_ad', name: 'app_add_Ad')]
    public function AddAd(ManagerRegistry $mr, Request $req): Response
    {
        $s = new Adoption();  //1 -instance

        $form = $this->createForm(AdoptionType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listAd');
        }

        return $this->render('/Back/Animal/AddAd.html.twig', [
            'formAdoption' => $form->createView(),
        ]);
    }

    #[Route('/update_ad/{adoptionId}', name: 'app_update_Ad')]
    public function updateAd(ManagerRegistry $mr, Request $req, $adoptionId): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(Adoption::class)->find($adoptionId);

        $form = $this->createForm(AdoptionType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('app_listAd');
        }

        return $this->render('/Back/Animal/UpdateAd.html.twig', [
            'formAdoption' => $form->createView(),
            'adoptionId' => $adoptionId,
        ]);
    }
    
    #[Route('/delete_ad/{adoptionId}', name: 'app_delete_Ad')]
     public function removeA(AdoptionRepository $repo, $adoptionId, ManagerRegistry $mr): Response
    {
    $adoption = $repo->find($adoptionId); 
    $em = $mr->getManager();
    $em->remove($adoption);
    $em->flush();
    
   
    return $this-> redirectToRoute('app_listAd');
    }

    #[Route('/List_adf', name: 'app_listAdF')]
    public function ListAdF(): Response
    {
        return $this->render('/Front/Animal/ListAd.html.twig', []);
    }
}
