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
        $books = $repo->findBy(['published' => true]); 

        return $this->render('book/index.html.twig', [
            'books' => $books,
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
            
         
            $book->setPublished(true);
            
          
            $author = $book->getAuthor();
            
            
            $author->setNbBooks($author->getNbBooks() + 1);
            
            
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
    
   
    $book = $em->getRepository(Book::class)->find($ref);
    
    

    $form = $this->createForm(BookType::class, $book);
    
    $form->handleRequest($req);
    
    if ($form->isSubmitted() && $form->isValid()) {
      
        $author = $book->getAuthor();
    
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
#[Route('/listBooks/{ref}', name: 'listBooks')]
public function listBooks(Request $request, BookRepository $bookRepo)
{
    $searchTerm = $request->query->get('search');
    $refValue = $request->attributes->get('ref'); 

    if ($searchTerm) {
        $books = $bookRepo->searchBookByRef($searchTerm);
    } else {
        $books = $bookRepo->findAll();
    }

    return $this->render('book/index.html.twig', [
        'books' => $books,
        
    ]);
}

#[Route('/listByAuthor', name: 'listByAuthor')]
public function listByAuthor(BookRepository $bookRepo)
{
  $books = $bookRepo->listBooksByAuthors();

  return $this->render('book/index.html.twig', [
    'books' => $books
  ]);
}
#[Route('/listBook', name: 'listBook')]
public function listBooksPublished(Request $request, BookRepository $bookRepo)
{
    $year = '2023'; // Année de référence
    $minBookCount = 10; // Nombre minimal de livres pour l'auteur

    $books = $bookRepo->showBooksByDateAndNbBooks($minBookCount, $year);

    return $this->render('book/index.html.twig', [
        'books' => $books,
    ]);
}
#[Route('/book/list/author/update/{category}', name: 'app_book_list_author_update', methods: ['GET'])]
public function updateCategoryAction(BookRepository $bookRepo)
{
    
    $result = $bookRepo->updateBooksCategoryByAuthor('Science-Fiction');

  
    return $this->render('book/listBookAuthor.html.twig', [
        'books' => $bookRepo->findAll(),
    ]);
}

  #[Route('/NbrCategory', name: 'book_Count')]
    function NbrCategory(BookRepository $repo)
    {
        $nbr = $repo->NbBookCategory();
        return $this->render('book/showNbrCategory.html.twig', [
            'nbr' => $nbr,
        ]);
    }

    
    #[Route('/showBookTitle', name: 'book_showBookByTitle')]
    function showTitleBook(BookRepository $repo)
    {
        $books = $repo->findBookByPublicationDate();
        return $this->render('book/showBooks.html.twig', [
            'books' => $books,
        ]);
    }

}
