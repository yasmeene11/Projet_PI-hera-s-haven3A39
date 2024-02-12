<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class IndexController extends AbstractController
{
    #[Route('/home', name: 'app_home')]
    public function indexhome(): Response
    {
        return $this->render('/Front/indexFront.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }

    #[Route('/login', name: 'app_login')]
    public function indexlogin(): Response
    {
        return $this->render('/index_Login/login.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }


    #[Route('/register', name: 'app_register')]
    public function indexregister(): Response
    {
        return $this->render('/index_Login/register.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }


    #[Route('/indexb', name: 'app_indexb')]
    public function indexBack(): Response
    {
        return $this->render('/Back/indexBack.html.twig', [
            'controller_name' => 'IndexController',
        ]);
    }
    
    
    
    

    
    
}
