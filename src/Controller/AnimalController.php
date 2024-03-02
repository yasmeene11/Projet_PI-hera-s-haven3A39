<?php

namespace App\Controller;

use App\Entity\Animal;
use App\Repository\AnimalRepository;
use App\Form\AnimalType;
use App\Form\QuizType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpClient\HttpClient;
use Psr\Log\LoggerInterface; 
use App\Entity\Account;
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
    public function removeA(AnimalRepository $repo, $animalId, EntityManagerInterface $em): Response
    {
        $animal = $repo->find($animalId);

        if (!$animal) {
            throw $this->createNotFoundException('Animal not found');
        }

        $animalStatus = $animal->getAnimalStatus();

        if ($animalStatus === 'Adopted' || $animalStatus === 'Pending'|| $animalStatus === 'Available' ) {
            $adoptions = $animal->getAdoptions();
            foreach ($adoptions as $adoption) {
                $em->remove($adoption);
            }
        } elseif ( $animalStatus === 'Here for Boarding') {
            $boardings = $animal->getBoardings();
            foreach ($boardings as $boarding) {
                $em->remove($boarding);
            }
        }

        
        $em->remove($animal);
        $em->flush();

        return $this->redirectToRoute('app_listA');
    }

    
   




    ////////front

    #[Route('/List_af', name: 'app_listAF')]
    public function ListAF(AnimalRepository $repo,SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
        $availableAnimals = $repo->findBy(['Animal_Status' => 'available']);
    
        return $this->render('/Front/Animal/ListA.html.twig', [
            'result' => $availableAnimals,
            'user' => $user,
        ]);
    }

   
    private function determineAnimal(array $answers): array
{
    $experience = (int)$answers['experience'];
    $daily_commitment = (int)$answers['daily_commitment'];
    $living_space = (int)$answers['living_space'];
    $budget = (int)$answers['budget'];
    $allergies = $answers['allergies'];

    $animalOptions = [
        'Dog' => 0,
        'Cat' => 0,
        'Fish' => 0,
         'Bird' => 0,
        'Reptile' => 0,
    ];

    if ($experience === 'Beginner') {
        $animalOptions['Fish'] += 5;
        $animalOptions['Bird'] += 2;
    } elseif ($experience === 'Intermediate') {
        $animalOptions['Dog'] += 3;
        $animalOptions['Fish'] += 3;
    } elseif ($experience === 'Experienced') {
        $animalOptions['Reptile'] += 2;
        $animalOptions['Cat'] += 5;
    }

    if ($daily_commitment === 'Low (1-2 hours)') {
        $animalOptions['Fish'] += 5;
        $animalOptions['Bird'] += 3;
        $animalOptions['Reptile'] += 2;
    } elseif ($daily_commitment === 'Medium (2-4 hours)') {
        $animalOptions['Dog'] += 3;
        $animalOptions['Cat'] += 3;
        $animalOptions['Bird'] += 2;
        $animalOptions['Reptile'] += 2;
    } elseif ($daily_commitment === 'High (4+ hours)') {
        $animalOptions['Dog'] += 5;
        $animalOptions['Cat'] += 5;
    }

        if ($living_space === 'Apartment') {
        $animalOptions['Fish'] += 5;
        $animalOptions['Bird'] += 3;
        $animalOptions['Reptile'] += 2;
    } elseif ($living_space === 'House') {
        $animalOptions['Dog'] += 5;
        $animalOptions['Cat'] += 5;
    }

    if ($budget === 'Low') {
        $animalOptions['Fish'] += 5;
        $animalOptions['Bird'] += 3;
        $animalOptions['Reptile'] += 2;
    } elseif ($budget === 'Medium') {
        $animalOptions['Bird'] += 5;
        $animalOptions['Cat'] += 5;
    }elseif ($budget === 'High') {
        $animalOptions['Dog'] += 5;
        $animalOptions['Cat'] += 5;
    }
    
    // Adjust scores based on allergies
    if ($allergies === 'Yes') {
        $animalOptions['Dog'] -= 10;
        $animalOptions['Cat'] -= 10;
    }

    arsort($animalOptions);

$highestScores = array_keys($animalOptions, reset($animalOptions), true);
$randomAnimalType = $highestScores[array_rand($highestScores)];

$entityManager = $this->getDoctrine()->getManager();

$recommendedAnimals = [];

// Adding the condition to fetch only available animals
$animals = $entityManager->getRepository(Animal::class)->findBy([
    'Animal_Type' => $randomAnimalType,
    'Animal_Status' => 'Available',
]);

if (!empty($animals)) {
    $recommendedAnimals[] = $animals[0];
}

return $recommendedAnimals;
    }
    






    #[Route('/animal_recommendation', name: 'animal_recommendation')]
    public function recommendation(Request $request,SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
        $form = $this->createForm(QuizType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $answers = $form->getData();
            
         $animal = $this->determineAnimal($answers);
   
            return $this->render('/Front/Animal/Recommendation.html.twig', [
                'recommendation' => $animal,
                'user' => $user,
            ]);
        }

        return $this->render('/Front/Animal/Quiz.html.twig', [
            'form' => $form->createView(),
            'user' => $user,
        ]);
    }
    







    #[Route('/filter', name: 'app_filter')]
    public function filterbyType(AnimalRepository $repo, Request $request,SessionInterface $session): Response
    {

        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
        $type = $request->get('type', null);
    
        $result = $repo->findBy(['Animal_Type' => $type, 'Animal_Status' => 'available']);
    
        return $this->render('/Front/Animal/ListA.html.twig', [
            'result' => $result,
            'user' => $user,
        ]);
    }


      #[Route('/desc_a{animalId}', name: 'app_descA')]
    public function DescA(AnimalRepository $repo, int $animalId,SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
        $animal = $repo->find($animalId);

    
        return $this->render('/Front/Animal/Description.html.twig', [
            'animal' => $animal,
            'user' => $user,
        ]);
    }


    #[Route('/List_bf', name: 'app_listBF')]
    public function ListBF(ManagerRegistry $mr, Request $req,SessionInterface $session): Response
    {
        
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
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
            'user' => $user,
        ]);
    }
    





























   
    
    
    
   
}
