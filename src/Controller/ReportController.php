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

        // Redirect to the list of reports after adding a new report
        return $this->redirectToRoute('app_listR');
    }

    return $this->render('Back/Appointment/AddR.html.twig', [
        'form' => $form->createView(),
    ]);
}

#[Route('/edit_r/{id}', name: 'app_edit_R')]
public function editR(Request $request, $id): Response
{
    $rapport = $this->getDoctrine()->getRepository(Rapport::class)->find($id);

    if (!$rapport) {
        throw new NotFoundHttpException("Rapport not found.");
    }

    // Store the original appointment ID
    $originalAppointmentId = $rapport->getAppointmentKey()->getAppointmentId();

    $form = $this->createForm(RapportType::class, $rapport);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $newAppointmentId = $rapport->getAppointmentKey()->getAppointmentId();

        // Check if the appointment ID has been changed to a new one that already has a report
        if ($originalAppointmentId !== $newAppointmentId) {
            $existingReport = $this->getDoctrine()->getRepository(Rapport::class)->findOneBy(['Appointment_Key' => $newAppointmentId]);
            if ($existingReport) {
                // njareb fel warning message!
                $this->addFlash('warning', 'The selected appointment is already associated with another report!.');
                // namel redirection mara okhra khater ki testit nalka l page
                return $this->redirectToRoute('app_edit_R', ['id' => $rapport->getRapportId()]); // Corrected here
            }
        }
        
        $this->getDoctrine()->getManager()->flush();
        $this->addFlash('success', 'Rapport updated successfully.');

        return $this->redirectToRoute('app_listR');
    }

    return $this->render('Back/Appointment/modifyRp.html.twig', [
        'rapport' => $rapport,
        'form' => $form->createView(),
    ]);
}

#[Route('/delete_r/{id}', name: 'app_delete_R')]
public function deleteR(Request $request, $id): Response
{
    $entityManager = $this->getDoctrine()->getManager();
    $rapport = $entityManager->getRepository(Rapport::class)->find($id);

    if (!$rapport) {
        throw new NotFoundHttpException("The rapport with id $id does not exist.");
    }

    $entityManager->remove($rapport);
    $entityManager->flush();

    $this->addFlash('success', 'Rapport deleted successfully.');

    return $this->redirectToRoute('app_listR');
}
}