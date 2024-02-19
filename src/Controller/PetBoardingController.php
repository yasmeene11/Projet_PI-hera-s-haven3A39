<?php

namespace App\Controller;

use App\Entity\Boarding;
use App\Entity\Animal;
use App\Entity\Account;
use App\Repository\BoardingRepository;
use App\Form\BoardingType;
use App\Repository\AnimalRepository;
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
        $pendingBoarding = $repo->count(['Boarding_Status' => 'Pending']);
        $totalBoarding = $repo->count([]);
        $cancelledBoarding = $repo->count(['Boarding_Status' => 'Cancelled']);
        $completedBoarding= $repo->count(['Boarding_Status' => 'Completed']);

        return $this->render('/Back/Animal/ListB.html.twig', [
            'result' => $result,
            'totalBoarding' => $totalBoarding,
        'completedBoarding' => $completedBoarding,
        'pendingBoarding' => $pendingBoarding,
        'cancelledBoarding' => $cancelledBoarding,
        ]);
    }

    #[Route('/boarding_statistics', name: 'app_boarding_statistics')]
    public function boardingStatistics(BoardingRepository $repo): Response
{
    $pendingBoarding = $repo->findBy(['Boarding_Status' => 'Pending']);
    $totalBoarding = $repo->count([]);
    $cancelledBoarding = $repo->count(['Boarding_Status' => 'Cancelled']);
    $completedBoarding= $repo->count(['Boarding_Status' => 'Completed']);

    return $this->render('/Front/Animal/ListB.html.twig', [
        'result' => $result,
        'totalBoarding' => $totalBoarding,
        'completedBoarding' => $completedBoarding,
        'pendingBoarding' => $pendingBoarding,
        'cancelledBoarding' => $cancelledBoarding,
    ]);
}
    
    #[Route('/add_b', name: 'app_add_B')]
    public function AddB(ManagerRegistry $mr, Request $req, AnimalRepository $animalRepository): Response
{
    $s = new Boarding();

    $form = $this->createForm(BoardingType::class, $s, [
        'animalRepository' => $animalRepository,
        'is_admin' => true,
    ]);
    

    $form->handleRequest($req);
    if ($form->isSubmitted() && $form->isValid()) {
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
    public function updateB(ManagerRegistry $mr, Request $req, $boardingId,AnimalRepository $animalRepository): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(Boarding::class)->find($boardingId);

        $form = $this->createForm(BoardingType::class, $s, [
            'animalRepository' => $animalRepository,
            'is_admin' => true,
        ]);

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

   #[Route('/add_ba{animalId}', name: 'app_listBa')]
public function AddBa(ManagerRegistry $mr, Request $request, $animalId,AnimalRepository $animalRepository): Response
{
    $animal = $mr->getRepository(Animal::class)->find($animalId);
    $s = new Boarding();
    $s->setBoardingStatus('Pending');
    $s->setBoardingFee(200);

    $accountId = 1;
    $user = $mr->getRepository(Account::class)->find($accountId);
    $s->setAccountKey($user);
    
    $s->setAnimalKey($animal);
    $form = $this->createForm(BoardingType::class, $s, ['animalRepository' => $animalRepository],['is_admin' => false]);
    
    $form->handleRequest($request); // Use $request instead of $req

    if ($form->isSubmitted() && $form->isValid()) {
        dump('Form data after handling request:', $form->getData());

        $em = $mr->getManager();
        $em->persist($s);   
        $em->flush();

        return $this->redirectToRoute('app_home');
    } else {
        dump('Form errors:', $form->getErrors(true, false));
    }

    return $this->render('/Front/Animal/ListBa.html.twig', [
        'formBoarding' => $form->createView(),
        'animalId' => $animalId,
    ]);
}


#[Route('/Hist_b', name: 'app_HistB')]
public function HistB(BoardingRepository $repo): Response
{
    $result = $repo->findAll();
    return $this->render('/Front/Animal/HistoryB.html.twig', [
        'result' => $result,
    ]);
}
   

}