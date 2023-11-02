<?php

namespace App\Controller;

use App\Entity\Book;
use App\Repository\BookRepository;
use App\Form\BookType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;


#[Route('/book')]
class BookController extends AbstractController
{
    #[Route('/book', name: 'app_book')]
    public function index(): Response
    {
        return $this->render('book/index.html.twig', [
            'controller_name' => 'BookController',
        ]);
    }
    #[Route('/affiche', name: 'affiche')]
    public function affiche(BookRepository $repo): Response
    {
        $result = $repo->findBy(['published' => true]); // Query the repository to get published books

        return $this->render('book/index.html.twig', [
            'result' => $result,
        ]);
        
    }
    #[Route('/addf', name: 'addf')]
    public function addf(ManagerRegistry $mr, Request $req): Response
    {
        $book = new Book();
        
        $form = $this->createForm(BookType::class, $book);
        
        $form->handleRequest($req);
        
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $mr->getManager();
            
            // Set the 'published' attribute to true
            $book->setPublished(true);
            
            // Get the author associated with this book
            $author = $book->getAuthor();
            
            // Increment the 'nb_books' attribute of the author
            $author->setNbBooks($author->getNbBooks() + 1);
            
            // Persist both the book and the author
            $em->persist($book);
            $em->persist($author);
            
            $em->flush();
            
            return $this->redirectToRoute('affiche');
        }
        
        return $this->render('book/add.html.twig', [
            'f' => $form->createView()
        ]);
    }
    #[Route('/update/{ref}', name: 'updatef')]
public function updatef(ManagerRegistry $mr, Request $req, $ref): Response
{
    $em = $mr->getManager();
    
    // Find the book you want to update by its ID
    $book = $em->getRepository(Book::class)->find($ref);
    
    

    $form = $this->createForm(BookType::class, $book);
    
    $form->handleRequest($req);
    
    if ($form->isSubmitted() && $form->isValid()) {
        // In this case, you don't need to change the 'published' attribute
        // because you are updating the existing book.
        
        // Get the author associated with this book
        $author = $book->getAuthor();
        
        // No need to increment 'nb_books' since you are updating, not creating
        
        // Persist the updated book and author
        $em->persist($book);
        $em->persist($author);
        
        $em->flush();
        
        return $this->redirectToRoute('affiche');
    }
    
    return $this->render('book/update.html.twig', [
        'f' => $form->createView()
    ]);
}
#[Route('/remove/{ref}', name: 'removef')]
public function removef(BookRepository $repo, $ref, ManagerRegistry $mr): Response
{
    $book = $repo->find($ref); 
    $em = $mr->getManager();
    $em->remove($book);
    $em->flush();
    
   
    return $this-> redirectToRoute('affiche');
}
#[Route('/show/{ref}', name: 'show')]
public function show(BookRepository $repo, int $ref): Response 
{
  $book = $repo->find($ref);

  return $this->render('book/show.html.twig', [
    'book' => $book
  ]);
}
}
