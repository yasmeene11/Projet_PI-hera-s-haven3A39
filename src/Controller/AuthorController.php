<?php

namespace App\Controller;

use App\Entity\Author;
use App\Repository\AuthorRepository;
use App\Form\AuthorType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

#[Route('/author')]
class AuthorController extends AbstractController
{
    #[Route('/author', name: 'app_author')]
    public function index(): Response
    {
        return $this->render('author/index.html.twig', [
            'controller_name' => 'AuthorController',
        ]);
    }
    #[Route('/fetch', name: 'fetch')]
    public function fetch(AuthorRepository $repo): Response
    {
        $result=$repo->findAll();
        return $this->render('author/index.html.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/adda', name: 'adda')]
   public function adda(ManagerRegistry $mr,Request $req): Response
   {
       
       $s=new Author();  //1 -instance
       
        $form=$this->createForm(AuthorType::class,$s);
      
       $form->handleRequest($req);
       if($form->isSubmitted()){
        $em=$mr->getManager();
        $em->persist($s);
        $em->flush();
        return $this->redirectToRoute('fetch');
       }
       
       return $this->render('author/add.html.twig',[
        'f'=>$form->createView()
       ]);


    }
    #[Route('/upadtea/{id}', name: 'updatea')]
    public function updatea(ManagerRegistry $mr, Request $req, $id): Response
    {
        $em = $mr->getManager();
        $s = $em->getRepository(Author::class)->find($id);

        $form = $this->createForm(AuthorType::class, $s);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            $em->flush();

            return $this->redirectToRoute('fetch');
        }

        return $this->render('author/update.html.twig', [
            'f' => $form->createView(),
            'id' => $id,
        ]);
    }
  
#[Route('/removea/{id}', name: 'removea')]
public function removea(AuthorRepository $repo, $id, ManagerRegistry $mr): Response
{
    $author = $repo->find($id); 
    $em = $mr->getManager();
    $em->removea($author);
    $em->flush();
    
   
    return $this-> redirectToRoute('fetch');
}

#[Route('/deleteauthors', name: 'deleteauthors')]
public function deleteauthors(AuthorRepository $authorRepository, EntityManagerInterface $entityManager): Response
{
    
    $authorsToDelete = $authorRepository->findBy(['nb_books' => 0]);

   
    foreach ($authorsToDelete as $author) {
        $entityManager->removea($author);
    }

    $entityManager->flush();

   
    return $this->redirectToRoute('fetch');
}
}

