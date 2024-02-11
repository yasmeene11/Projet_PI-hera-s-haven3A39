<?php

namespace App\Controller;

use App\Entity\Animal;
use App\Repository\AnimalRepository;
use App\Form\AnimalType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class AnimalController extends AbstractController
{
    #[Route('/List_a', name: 'app_listA')]
    public function ListA(AnimalRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/Animal/ListA.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/add_a', name: 'app_add_A')]
    public function AddA(ManagerRegistry $mr, Request $req): Response
    {
        $s = new Animal();  //1 -instance

        $form = $this->createForm(AnimalType::class, $s);

        $form->handleRequest($req);
        if ($form->isSubmitted()) {
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listA');
        }

        return $this->render('/Back/Animal/AddA.html.twig', [
            'formAnimal' => $form->createView(),
        ]);
    }

    #[Route('/update_a/{animalId}', name: 'app_update_A')]
    public function updateA(ManagerRegistry $mr, Request $req, $animalId): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(Animal::class)->find($animalId);

        $form = $this->createForm(AnimalType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('app_listA');
        }

        return $this->render('/Back/Animal/UpdateA.html.twig', [
            'formAnimal' => $form->createView(),
            'animalId' => $animalId,
        ]);
    }
    
    #[Route('/delete_a/{animalId}', name: 'app_delete_A')]
     public function removeA(AnimalRepository $repo, $animalId, ManagerRegistry $mr): Response
    {
    $animal = $repo->find($animalId); 
    $em = $mr->getManager();
    $em->remove($animal);
    $em->flush();
    
   
    return $this-> redirectToRoute('app_listA');
    }

}
