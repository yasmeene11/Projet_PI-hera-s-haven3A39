<?php

namespace App\Controller;

use App\Entity\Adoption;
use App\Repository\AdoptionRepository;
use App\Form\AdoptionType;
use App\Entity\Animal;
use App\Repository\AnimalRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\Security\Core\Security;
use App\Entity\Account; 


class AdoptionController extends AbstractController
{
    #[Route('/List_ad', name: 'app_listAd')]
    public function ListAd(AdoptionRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/Animal/ListAd.html.twig', [
            'result' => $result,
        ]);
    }

    #[Route('/add_ad', name: 'app_add_Ad')]
    public function AddAd(ManagerRegistry $mr, Request $req): Response
    {
        $s = new Adoption(); 

        
        $form = $this->createForm(AdoptionType::class, $s, ['is_admin' => true]);
        $form->handleRequest($req);
        
        if ($form->isSubmitted() && $form->isValid()) {
            
            $em = $mr->getManager();
            $em->persist($s);
            $em->flush();
            return $this->redirectToRoute('app_listAd');
        }

        return $this->render('/Back/Animal/AddAd.html.twig', [
            'formAdoption' => $form->createView(),
        ]);
    }

    #[Route('/update_ad/{adoptionId}', name: 'app_update_Ad')]
public function updateAd(ManagerRegistry $mr, Request $req, $adoptionId): Response
{
    $em = $mr->getManager();
    $adoption = $em->getRepository(Adoption::class)->find($adoptionId);

    $form = $this->createForm(AdoptionType::class, $adoption, ['is_admin' => true]);

    $form->handleRequest($req);

    if ($form->isSubmitted() && $form->isValid()) {
        $em = $mr->getManager();
        if ($adoption->getAdoptionStatus() === 'Cancelled') {
            $animal = $adoption->getAnimalKey();
            $animal->setAnimalStatus('Available');
            $em->persist($animal);
        }

        $em->flush();

        return $this->redirectToRoute('app_listAd');
    }

    return $this->render('/Back/Animal/UpdateAd.html.twig', [
        'formAdoption' => $form->createView(),
        'adoptionId' => $adoptionId,
    ]);
}

    
    #[Route('/delete_ad/{adoptionId}', name: 'app_delete_Ad')]
     public function removeA(AdoptionRepository $repo, $adoptionId, ManagerRegistry $mr): Response
    {
    $adoption = $repo->find($adoptionId); 
    $em = $mr->getManager();
    $em->remove($adoption);
    $em->flush();
    
   
    return $this-> redirectToRoute('app_listAd');
    }


    #[Route('List_adf{animalId}', name: 'app_listAdF')]
    public function AddAdF(ManagerRegistry $mr, Request $req, $animalId): Response
    {
        $animal = $mr->getRepository(Animal::class)->find($animalId);
    
        $adoption = new Adoption();
    
      
        $adoption->setAdoptionStatus('Pending');
        $adoption->setAdoptionFee(200);
    
        
        $accountId = 1;
        $user = $mr->getRepository(Account::class)->find($accountId);
        $adoption->setAccountKey($user);
    
        $adoption->setAnimalKey($animal);
    
        $form = $this->createForm(AdoptionType::class, $adoption, ['is_admin' => false]);
        dump('Form data before handling request:', $form->getData());
    
        $form->handleRequest($req);
    
        if ($form->isSubmitted() && $form->isValid()) {
            
            dump('Form data after handling request:', $form->getData());
    
            $em = $mr->getManager();
            $em->persist($adoption);
            $animal->setAnimalStatus('Pending');
            $em->persist($animal);
            $em->flush();
    
            return $this->redirectToRoute('app_home');
        } else {
            
            dump('Form errors:', $form->getErrors(true, false));
        }
    
        return $this->render('/Front/Animal/ListAd.html.twig', [
            'formAdoption' => $form->createView(),
        ]);
    }


}





