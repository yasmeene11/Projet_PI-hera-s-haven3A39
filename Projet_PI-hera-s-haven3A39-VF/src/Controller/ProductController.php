<?php

namespace App\Controller;

use App\Entity\Product;
use App\Form\FormProductType;
use App\Repository\ProductRepository;
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
    #[Route('/add_p', name: 'app_add_P')]
    public function AddP(ManagerRegistry $mr,Request $req): Response
    {
        $s=new Product();
        $form=$this->createForm(FormProductType::class,$s);
        $form->handleRequest($req);
        if($form->isSubmitted()&& ($form->isValid())){
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
public function removeP($productId,ManagerRegistry $mr,ProductRepository $repo) : Response {
$product=$repo->find($productId);
$em=$mr->getManager();
$em->remove($product);
$em->flush();
return $this->redirectToRoute('app_listP');
}

}
