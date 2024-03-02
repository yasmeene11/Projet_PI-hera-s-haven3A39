<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use App\Service\NotificationService;
use App\Entity\Adoption;
use App\Entity\Account;
use App\Repository\AdoptionRepository;
use Symfony\Component\HttpFoundation\Session\Flash\FlashBagInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;




class IndexController extends AbstractController
{
    #[Route('/home', name: 'app_home')]
    public function indexhome(SessionInterface $session): Response
    {
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);

        return $this->render('/Front/indexFront.html.twig', [
            'controller_name' => 'IndexController',
            'user' => $user, // Pass the user information to the template
        ]);
    }



    
    #[Route('/homev', name: 'app_homeV')]
    public function indexhomeV(): Response
    {
        return $this->render('/Front/indexFrontV.html.twig', [
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


    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/indexb', name: 'app_indexb')]
    public function indexBack(Request $request, SessionInterface $session): Response
    {
      //  $userId = $session->get('user_id');

        // Fetch user information from the database
       // $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);

        // Check if the approve button is clicked in the notification
        if ($request->query->get('approve') === 'true') {
            // Retrieve the adoption ID from the session flash bag
            $adoptionId = $session->getFlashBag()->get('adoption_id')[0] ?? null;

            if ($adoptionId) {
                // Fetch the adoption object based on the ID (replace this with your own logic)
                $adoption = $this->entityManager->getRepository(Adoption::class)->find($adoptionId);

                if ($adoption) {
                    // Redirect to the UpdateAd action with the adoption ID as a parameter
                    return $this->redirectToRoute('app_update_Adf', ['adoptionId' => $adoptionId]);
                }
            }
        }

        // Fetch the adoption object to display in the notification (replace this with your own logic)
        $adoption = $this->entityManager->getRepository(Adoption::class)->findOneBy([]);

        // Render the indexBack.html.twig template
        return $this->render('/Back/indexBack.html.twig', [
            'controller_name' => 'IndexController',
            'adoption' => $adoption,
           // 'user' => $user,
        ]);
    }
    
    
}
