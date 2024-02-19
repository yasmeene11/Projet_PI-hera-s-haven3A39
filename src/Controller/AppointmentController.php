<?php

namespace App\Controller;

use App\Entity\Appointment;
use App\Form\AppointmentType;
use App\Form\AppointmentTypef;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\AppointmentRepository;
use Doctrine\ORM\EntityManagerInterface; 

class AppointmentController extends AbstractController
{
    #[Route('/fetch_ap', name: 'app_fetchAp')]
    public function fetchAp(): Response
    {
        $appointments = $this->getDoctrine()->getRepository(Appointment::class)->findAll();

        return $this->render('/Back/Appointment/ListAp.html.twig', [
            'appointments' => $appointments,
        ]);
    }

    #[Route('/List_ap', name: 'app_listAp')]
    public function listAp(): Response
    {
        $appointments = $this->getDoctrine()->getRepository(Appointment::class)->findAll();
        return $this->render('/Back/Appointment/ListAp.html.twig', [
            'appointments' => $appointments,
        ]);
    }

    #[Route('/add_ap', name: 'app_add_Ap')]
    public function addAp(Request $request): Response
    {
        $appointment = new Appointment();

        $form = $this->createForm(AppointmentType::class, $appointment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($appointment);
            $entityManager->flush();

            $this->addFlash('success', 'Appointment added successfully.');
            return $this->redirectToRoute('app_listAp');
        }

        return $this->render('/Back/Appointment/AddAp.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/delete_ap/{id}', name: 'app_delete_Ap')]
    public function deleteAp(Request $request, EntityManagerInterface $entityManager, int $id): Response
    {
        // Fetch the Appointment entity manually
        $appointment = $entityManager->getRepository(Appointment::class)->find($id);
    
        // Check if the appointment exists
        if (!$appointment) {
            throw $this->createNotFoundException('Appointment not found');
        }
    
        // Remove the appointment
        $entityManager->remove($appointment);
        $entityManager->flush();
    
        // Redirect to the list of appointments
        $this->addFlash('success', 'Appointment successfully deleted.');
        return $this->redirectToRoute('app_listAp');
    }

    #[Route('/modify_ap/{id}', name: 'app_modify_ap')]
    public function modifyAp(Request $request, AppointmentRepository $appointmentRepository, int $id): Response
    {
        $appointment = $appointmentRepository->find($id);

        if (!$appointment) {
            throw $this->createNotFoundException('Appointment not found');
        }

        $form = $this->createForm(AppointmentType::class, $appointment, [
            'include_status_field' => true, 
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $this->addFlash('success', 'Appointment updated successfully.');

            return $this->redirectToRoute('app_listAp');
        }
        return $this->render('/Back/Appointment/modifyAp.html.twig', [
            'form' => $form->createView(),
            'id' => $appointment->getAppointmentId(),
        ]);
    }

    #[Route('/List_apf', name: 'app_listApF')]
    public function ListApF(): Response
    {
       // Fetch all appointments from the database
    $appointments = $this->getDoctrine()->getRepository(Appointment::class)->findAll();

    // Render the template and pass the appointments variable to it
    return $this->render('/Front/Appointment/ListAp.html.twig', [
        'appointments' => $appointments,
    ]);
    }


    #[Route('/modify_apf{id}', name: 'app_modify_apf')]
    public function modifyApF(Request $request, AppointmentRepository $appointmentRepository, int $id): Response
    {
        $appointment = $appointmentRepository->find($id);
    
        if (!$appointment) {
            throw $this->createNotFoundException('Appointment not found');
        }
    
        $form = $this->createForm(AppointmentTypef::class, $appointment); // Use Appointment entity in form
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->flush();
    
            $this->addFlash('success', 'Appointment updated successfully.');
            return $this->redirectToRoute('app_listApF'); // Adjust the route name as necessary
        }
    
        return $this->render('/Front/Appointment/modifyAP.html.twig', [
            'form' => $form->createView(),
            'id' => $appointment->getAppointmentId(), // Correct method to get the appointment ID
        ]);
    }


    #[Route('/add_rf', name: 'app_add_RF')]
    public function addRF(Request $request): Response
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
            return $this->redirectToRoute('app_listRF');
        }
    
        return $this->render('Front/Appointment/addRp.html.twig', [
            'form' => $form->createView(),
        ]);
    }



}

