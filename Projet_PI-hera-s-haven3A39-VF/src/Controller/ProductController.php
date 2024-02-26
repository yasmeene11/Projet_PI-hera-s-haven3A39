<?php

namespace App\Controller;

use App\Entity\Product;
use App\Form\FormProductType;
use App\Repository\ProductRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
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
    public function ListPF(ProductRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Front/Product/ListP.html.twig', [
            'result' => $result,
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
public function triP(ProductRepository $repo, Request $request): Response
{
    $productType = $request->get('productType', null);
    $result = $repo->findAllOrderedByCategory($productType);

    return $this->render('/Front/Product/ListP.html.twig', [
        'result' => $result,
    ]);
}

#[Route('/triPn', name: 'triPn')]
public function triPn(ProductRepository $repo):Response
{
    $result = $repo->orderbyNameDESC();
    return $this->render('/Back/Product/ListP.html.twig', [
        'result' => $result,
    ]);
}
}