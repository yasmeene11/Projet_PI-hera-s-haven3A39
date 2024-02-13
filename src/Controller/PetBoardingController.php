<?php

namespace App\Controller;

use App\Entity\Boarding;
use App\Repository\BoardingRepository;
use App\Form\BoardingType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class PetBoardingController extends AbstractController
{
    #[Route('/List_b', name: 'app_listB')]
    public function ListB(BoardingRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/Animal/ListB.html.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/add_b', name: 'app_add_B')]
    public function AddB(ManagerRegistry $mr, Request $req): Response
    {
        $s = new Boarding();  //1 -instance

        $form = $this->createForm(BoardingType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listB');
        }

        return $this->render('/Back/Animal/AddB.html.twig', [
            'formBoarding' => $form->createView(),
        ]);
    }

    #[Route('/update_b/{boardingId}', name: 'app_update_B')]
    public function updateB(ManagerRegistry $mr, Request $req, $boardingId): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(Boarding::class)->find($boardingId);

        $form = $this->createForm(BoardingType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('app_listB');
        }

        return $this->render('/Back/Animal/UpdateB.html.twig', [
            'formBoarding' => $form->createView(),
            'boardingId' => $boardingId,
        ]);
    }

    #[Route('/delete_b/{boardingId}', name: 'app_delete_B')]
    public function removeB(BoardingRepository $repo, $boardingId, ManagerRegistry $mr): Response
   {
   $boarding = $repo->find($boardingId); 
   $em = $mr->getManager();
   $em->remove($boarding);
   $em->flush();
   
  
   return $this-> redirectToRoute('app_listB');
   }


    #[Route('/List_bf', name: 'app_listBF')]
    public function ListBF(): Response
    {
        return $this->render('/Front/Animal/ListB.html.twig', []);
    }
}
