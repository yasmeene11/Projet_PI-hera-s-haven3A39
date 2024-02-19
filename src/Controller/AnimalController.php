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
use Symfony\Component\HttpFoundation\File\UploadedFile;


class AnimalController extends AbstractController
{
    #[Route('/List_a', name: 'app_listA')]
    public function ListA(AnimalRepository $repo): Response
    {
        $result = $repo->findAll();
        $totalAnimals = $repo->count([]);  
        $adoptedAnimals = $repo->count(['Animal_Status' => 'Adopted']);
        $availableAnimals = $repo->count(['Animal_Status' => 'Available']);
        $boardingAnimals = $repo->count(['Animal_Status' => 'Here for Boarding']);  // Count adopted animals
    
        return $this->render('/Back/Animal/ListA.html.twig', [
            'result' => $result,
            'totalAnimals' => $totalAnimals,
            'adoptedAnimals' => $adoptedAnimals,
            'availableAnimals' => $availableAnimals,
            'boardingAnimals' => $boardingAnimals,
        ]);
    }
    

    #[Route('/add_a', name: 'app_add_A')]
    public function AddA(ManagerRegistry $mr, Request $req): Response
    {
        $animal = new Animal();
        $form = $this->createForm(AnimalType::class, $animal, ['is_admin' => true]);
        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $imageFile = $form->get('Animal_Image')->getData(); 

            if ($imageFile instanceof UploadedFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('kernel.project_dir').'/public/animal_images/',
                    $newFilename
                );
                $animal->setAnimalImage($newFilename);
            }

            $em = $mr->getManager();
            $em->persist($animal);
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
        $animal = $em->getRepository(Animal::class)->find($animalId);

        $form = $this->createForm(AnimalType::class, $animal, ['is_admin' => true]);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
          
            $imageFile = $form->get('Animal_Image')->getData();

            if ($imageFile instanceof UploadedFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('kernel.project_dir').'/public/animal_images/',
                    $newFilename
                );
                $animal->setAnimalImage($newFilename);
            }

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

        return $this->redirectToRoute('app_listA');
    }

    
   

    #[Route('/List_af', name: 'app_listAF')]
    public function ListAF(AnimalRepository $repo): Response
    {
        $availableAnimals = $repo->findBy(['Animal_Status' => 'available']);
    
        return $this->render('/Front/Animal/ListA.html.twig', [
            'result' => $availableAnimals,
        ]);
    }
    


    #[Route('/animal_statistics', name: 'app_animal_statistics')]
    public function animalStatistics(AnimalRepository $repo): Response
{
    $availableAnimals = $repo->findBy(['Animal_Status' => 'Available']);
    $totalAnimals = $repo->count([]);
    $adoptedAnimals = $repo->count(['Animal_Status' => 'Adopted']);
    $boardingAnimals = $repo->count(['Animal_Status' => 'Here for Boarding']);

    return $this->render('/Front/Animal/ListA.html.twig', [
        'result' => $result,
        'totalAnimals' => $totalAnimals,
        'availableAnimals' => $availableAnimals,
        'adoptedAnimals' => $adoptedAnimals,
        'boardingAnimals' => $boardingAnimals,
    ]);
}

      #[Route('/desc_a{animalId}', name: 'app_descA')]
    public function DescA(AnimalRepository $repo, int $animalId): Response
    {
        $animal = $repo->find($animalId);

    
        return $this->render('/Front/Animal/Description.html.twig', [
            'animal' => $animal,
        ]);
    }


    #[Route('/List_bf', name: 'app_listBF')]
    public function ListBF(ManagerRegistry $mr, Request $req): Response
    {
        $animal = new Animal();

        $animal->setAnimalStatus('Here for Boarding');

       

        $form = $this->createForm(AnimalType::class, $animal, ['is_admin' => false]);
        
       
        dump('Form data before handling request:', $form->getData());
       
        $form->handleRequest($req);
    
        if ($form->isSubmitted() && $form->isValid()) {
            
            dump('Form data after handling request:', $form->getData());
            $imageFile = $form->get('Animal_Image')->getData();
    
            if ($imageFile instanceof UploadedFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('kernel.project_dir').'/public/animal_images/',
                    $newFilename
                );
                $animal->setAnimalImage($newFilename);
            }
            $em = $mr->getManager();
            $em->persist($animal);
            $em->flush();
    
            
            return $this->redirectToRoute('app_listBa', ['animalId' => $animal->getanimalId()]);
        }else {
            
            dump('Form errors:', $form->getErrors(true, false));
        }
    
        return $this->render('/Front/Animal/ListB.html.twig', [
            'formAnimal' => $form->createView(),
        ]);
    }
    


    
    
   
}
