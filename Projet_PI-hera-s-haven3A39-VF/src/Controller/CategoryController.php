<?php

namespace App\Controller;

use App\Entity\Category;
use App\Form\FormCategoryType;
use App\Repository\CategoryRepository;
use App\Form\CategoryType;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class CategoryController extends AbstractController
{
    #[Route('/List_cat', name: 'app_listcat')]
    public function Listcat(CategoryRepository $repo): Response
    {
        $result = $repo->findAll();
        return $this->render('/Back/category/Listcat.html.twig', [
            'result' => $result,
        ]);
    }
    #[Route('/add_cat', name: 'app_add_cat')]
    public function Addcat(ManagerRegistry $mr,Request $req): Response
    {
        $s=new Category();
        $form=$this->createForm(FormCategoryType::class,$s);
        $form->handleRequest($req);
        if($form->isSubmitted()&& $form->isValid()){
         $em=$mr->getManager();
         $em->persist($s);
         $em->flush();
         return $this->redirectToRoute('app_listcat');
        }
        return $this->render('/Back/Category/Addcat.html.twig', [
            'formCategory'=>$form->createView()
        ]);
    }
    #[Route('/update/{categoryId}', name: 'updatecat')]
    public function updatecat($categoryId,CategoryRepository $repo, ManagerRegistry $mr, Request $req):Response
    {
    $s=$repo->find($categoryId) ;
    $form=$this->createForm(FormCategoryType::class,$s) ; 
        $form->handleRequest($req);
    if($form->isSubmitted() && $form->isValid())  {
        $em=$mr->getManager();
        $em->persist($s);
        $em->flush();
       return $this->redirectToRoute('app_listcat');
    }
    return $this->renderForm('/Back/Category/Updatecat.html.twig',[
        'formCategory'=>$form
    ]);
}
#[Route('/remove/{categoryId}', name: 'removecat')]
public function removecat($categoryId, ManagerRegistry $mr, CategoryRepository $repo): Response
{
    $em = $mr->getManager();
    $category = $repo->find($categoryId);

    if (!$category) {
        throw $this->createNotFoundException('Category not found');
    }

    foreach ($category->getProducts() as $product) {
        $em->remove($product);
    }

    
    $em->remove($category);
    $em->flush();

    return $this->redirectToRoute('app_listcat');
}

}
