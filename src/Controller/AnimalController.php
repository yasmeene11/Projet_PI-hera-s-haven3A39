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
 // Added this line for UploadedFile

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
        $animal = new Animal();
        $form = $this->createForm(AnimalType::class, $animal);
        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            // Handle file upload
            $imageFile = $form->get('Animal_Image')->getData(); // Changed 'imageFile' to 'Animal_Image'

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

        $form = $this->createForm(AnimalType::class, $animal);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            // Handle file upload
            $imageFile = $form->get('Animal_Image')->getData(); // Changed 'imageFile' to 'Animal_Image'

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
    public function ListAF(): Response
    {
        return $this->render('/Front/Animal/ListA.html.twig', []);
    }
}
