<?php

namespace App\Controller;

use App\Entity\Rapport;
use App\Form\RapportType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Entity\Account;
class ReportController extends AbstractController
{

    private function getNumberOfReports(): int {
        $rapportRepository = $this->getDoctrine()->getRepository(Rapport::class);
        return $rapportRepository->createQueryBuilder('r')
            ->select('COUNT(r.rapportId)')
            ->getQuery()
            ->getSingleScalarResult();
    }

    #[Route('/List_r', name: 'app_listR')]
    public function listR(): Response
    {
        $rapports = $this->getDoctrine()->getRepository(Rapport::class)->findAll();
        $numberOfReports = $this->getNumberOfReports(); 
        return $this->render('Back/Appointment/ListR.html.twig', [
            'rapports' => $rapports,
            'numberOfReports' => $numberOfReports,

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




#[Route('/List_rf', name: 'app_listRF')]
public function ListRF(SessionInterface $session): Response
{
    $userId = $session->get('user_id');

        // Fetch user information from the database
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    $rapports = $this->getDoctrine()->getRepository(Rapport::class)->findAll();
    
    return $this->render('Front/Appointment/ListR.html.twig', [
        'rapports' => $rapports,
        'user' => $user,
    ]);

}

#[Route('/edit_rf{id}', name: 'app_edit_RF')]
public function editRF(Request $request, $id,SessionInterface $session): Response
{
    $userId = $session->get('user_id');

    // Fetch user information from the database
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    $rapport = $this->getDoctrine()->getRepository(Rapport::class)->find($id);

    if (!$rapport) {
        throw $this->createNotFoundException("Rapport not found.");
    }

    $originalAppointmentId = $rapport->getAppointmentKey()->getAppointmentId();

    $form = $this->createForm(RapportType::class, $rapport);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $newAppointmentId = $rapport->getAppointmentKey()->getAppointmentId();

        if ($originalAppointmentId !== $newAppointmentId) {
            $existingReport = $this->getDoctrine()->getRepository(Rapport::class)->findOneBy(['appointmentKey' => $newAppointmentId]);
            if ($existingReport) {
                $this->addFlash('warning', 'The selected appointment is already associated with another report!');
                return $this->redirectToRoute('app_edit_RF', ['id' => $rapport->getRapportId()]);
            }
        }

        $this->getDoctrine()->getManager()->flush();
        $this->addFlash('success', 'Rapport updated successfully.');

        return $this->redirectToRoute('app_listRF'); // Ensure this redirects to the correct route
    }

    return $this->render('Front/Appointment/modifyRp.html.twig', [
        'rapport' => $rapport,
        'form' => $form->createView(),
        'user' => $user,
    ]);
}

#[Route('/add_rf', name: 'app_add_RF')]
    public function addRF(Request $request,SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

    // Fetch user information from the database
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
        $rapport = new Rapport();
        $form = $this->createForm(RapportType::class, $rapport);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($rapport);
            $entityManager->flush();
    
            $this->addFlash('success', 'Rapport added successfully.');
    
            // Redirect to the list of reports after adding a new report
            return $this->redirectToRoute('app_listRF');
        }
    
        return $this->render('Front/Appointment/addRp.html.twig', [
            'form' => $form->createView(),
            'user' => $user,
        ]);
    }




    #[Route('/delete_rf/{id}', name: 'app_delete_Rf')]
    public function deleteRf(Request $request, $id): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $rapport = $entityManager->getRepository(Rapport::class)->find($id);
    
        if (!$rapport) {
            throw new NotFoundHttpException("The rapport with id $id does not exist.");
        }
    
        $entityManager->remove($rapport);
        $entityManager->flush();
    
        $this->addFlash('success', 'Rapport deleted successfully.');
    
        return $this->redirectToRoute('app_listRF');
    }



}