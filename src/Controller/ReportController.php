<?php

namespace App\Controller;

use App\Entity\Rapport;
use App\Form\RapportType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ReportController extends AbstractController
{
    #[Route('/List_r', name: 'app_listR')]
    public function listR(): Response
    {
        $rapports = $this->getDoctrine()->getRepository(Rapport::class)->findAll();
        return $this->render('Back/Appointment/ListR.html.twig', [
            'rapports' => $rapports,
        ]);
    }

    #[Route('/add_r', name: 'app_add_R')]
    public function addR(Request $request): Response
    {
        $rapport = new Rapport();
        $form = $this->createForm(RapportType::class, $rapport);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($rapport);
            $entityManager->flush();
    
            $this->addFlash('success', 'Rapport added successfully.');
    
            return $this->redirectToRoute('app_listR');
        }
    
        return $this->render('Back/Appointment/AddR.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/edit_r/{id}', name: 'app_edit_R')]
    public function editR(Request $request, Rapport $rapport): Response
    {
        $form = $this->createForm(RapportType::class, $rapport);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $this->addFlash('success', 'Rapport updated successfully.');
            return $this->redirectToRoute('app_listR');
        }

        return $this->render('Back/Appointment/EditR.html.twig', [
            'rapport' => $rapport,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/delete_r/{id}', name: 'app_delete_R')]
    public function deleteR(Request $request, Rapport $rapport): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($rapport);
        $entityManager->flush();

        $this->addFlash('success', 'Rapport deleted successfully.');
        return $this->redirectToRoute('app_listR');
    }

    #[Route('/List_rf', name: 'app_listRF')]
    public function ListRF(): Response
    {
        // This method can be adjusted similarly if you need to pass data to the template
        return $this->render('/Front/Appointment/ListR.html.twig', []);
    }
}