<?php

namespace App\Controller;

use App\Entity\Product;
use App\Form\FormProductType;
use App\Repository\ProductRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Entity\Account;
class ProductController extends AbstractController

{
    #[Route('/List_p', name: 'app_listP')]
    public function ListP(ProductRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/Product/ListP.html.twig', [
            'result' => $result,
        ]);
    }
    
    #[Route('/List_pf', name: 'app_listPF')]
    public function ListPF(ProductRepository $repo, Request $req, PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);

        $result = $repo->findAll();

        $totalItems = count($result);
        $itemsPerPage = max(1, ceil($totalItems * 0.2));

        $pagination = $paginator->paginate(
            $result,
            $req->query->getInt('page', 1),
            $itemsPerPage
        );

        return $this->render('/Front/Product/ListP.html.twig', [
            'result' => $pagination,
            'user' => $user,
        ]);
    }
    #[Route('/add_p', name: 'app_add_P')]
    public function AddP(ManagerRegistry $mr,Request $req): Response
    {
        $s=new Product();
        $form=$this->createForm(FormProductType::class,$s);
        $form->handleRequest($req);
        if($form->isSubmitted()&& ($form->isValid())){
            $imageFile = $form->get('Product_Image')->getData(); 

            if ($imageFile instanceof UploadedFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('kernel.project_dir').'/public/Product_images/',
                    $newFilename
                );
                $s->setProductImage($newFilename);
            }

         $em=$mr->getManager();
         $em->persist($s);
         $em->flush();
         return $this->redirectToRoute('app_listP');
        }
        return $this->render('/Back/Product/AddP.html.twig', [
            'formProduct'=>$form->createView()
        ]);
    }
    
    #[Route('/updateP/{productId}', name: 'updateP')]
    public function updateP($productId,ProductRepository $repo, ManagerRegistry $mr, Request $req):Response
    {
    $s=$repo->find($productId) ;
    $form=$this->createForm(FormProductType::class,$s) ; 
        $form->handleRequest($req);
    if($form->isSubmitted()&& ($form->isValid()))  {
        $imageFile = $form->get('Product_Image')->getData();

        if ($imageFile instanceof UploadedFile) {
            $newFilename = uniqid().'.'.$imageFile->guessExtension();
            $imageFile->move(
                $this->getParameter('kernel.project_dir').'/public/Product_images/',
                $newFilename
            );
            $s->setProductImage($newFilename);
        }

        $em=$mr->getManager();
        $em->persist($s);
        $em->flush();
       return $this->redirectToRoute('app_listP');
    }
    return $this->renderForm('/Back/Product/UpdateP.html.twig',[
        'formProduct'=>$form
    ]);
}
#[Route('/removeP/{productId}', name: 'removeP')]
public function removeP($productId, ManagerRegistry $mr, ProductRepository $repo): Response
{
    $product = $repo->find($productId);
    $donationProducts = $product->getDonationProducts();
    $em = $mr->getManager();
    foreach ($donationProducts as $donationProduct) {
        $em->remove($donationProduct);
    }
    $em->remove($product);
    $em->flush();
    return $this->redirectToRoute('app_listP');
}




#[Route('/searchP', name: 'searchP')]
public function searchP(Request $request, ProductRepository $repo): Response
{
    $test = $request->request->get('test');

    $result = $repo->searchP($test);

    return $this->render('/Back/Product/ListP.html.twig', [
        'result' => $result,
    ]);
    return $this->redirectToRoute('app_listP');
}



#[Route('/triP', name: 'triP')]
public function triP(ProductRepository $repo, Request $request, PaginatorInterface $paginator,SessionInterface $session): Response
{
    $userId = $session->get('user_id');

    // Fetch user information from the database
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    $productType = $request->get('productType', null);
    $result = $repo->findAllOrderedByCategory($productType);

    $totalItems = count($result);
    $itemsPerPage = max(1, ceil($totalItems * 0.2));

    $pagination = $paginator->paginate(
        $result,
        $request->query->getInt('page', 1),
        $itemsPerPage
    );

    return $this->render('/Front/Product/ListP.html.twig', [
        'result' => $pagination,
        'user' => $user,
    ]);
}



   #[Route('/search', name: 'search_page')]
    public function searchPage(): Response
    {
        return $this->render('Front/Product/listP.html.twig');
    }

    #[Route('/search/result', name: 'search_result')]
    public function searchResult(Request $request, EntityManagerInterface $entityManager): JsonResponse
    {
        $query = $request->query->get('searchValue');
        $result = $entityManager->getRepository(Product::class)->searchPF($query);

        return $this->json($result);
    }
    #[Route('/update-rating/{productId}/{rating}', name: 'update_rating')]
    public function updateRating($productId, $rating,EntityManagerInterface $entityManager,ManagerRegistry $mr): JsonResponse
    {
        $product = $entityManager->getRepository(Product::class)->find($productId);

        if (!$product instanceof Product) {
            return $this->json(['success' => false, 'message' => 'Product not found'], 404);
        }
        $product->setRating($rating);

        $entityManager=$mr->getManager();
        $entityManager->persist($product);
        $entityManager->flush();
        return $this->json(['success' => true]);
    }
}