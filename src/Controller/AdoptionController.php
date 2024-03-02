<?php

namespace App\Controller;

use App\Entity\Adoption;
use App\Repository\AdoptionRepository;
use App\Form\AdoptionType;
use App\Entity\Animal;
use App\Entity\CashRegister;
use App\Repository\AnimalRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\Security\Core\Security;
use App\Entity\Account; 
use Mpdf\Mpdf;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Session\Flash\FlashBagInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Endroid\QrCode\QrCode;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\Color\Color;
use Endroid\QrCode\Label\Label;
use Endroid\QrCode\Label\Font\NotoSans;


class AdoptionController extends AbstractController
{
    #[Route('/List_ad', name: 'app_listAd')]
    public function ListAd(AdoptionRepository $repo): Response
    {
        $result = $repo->findAll();
        $totalAdoptions = $repo->count([]);  
        $adoptedAdoptions = $repo->count(['Adoption_Status' => 'Adopted']);
        $pendingAdoptions = $repo->count(['Adoption_Status' => 'Pending']);
        $cancelledAdoptions = $repo->count(['Adoption_Status' => 'Cancelled']);
        return $this->render('/Back/Animal/ListAd.html.twig', [
        'result' => $result,
        'totalAdoptions' => $totalAdoptions,
        'pendingAdoptions' => $pendingAdoptions,
        'adoptedAdoptions' => $adoptedAdoptions,
        'cancelledAdoptions' => $cancelledAdoptions,
        ]);
    }

    #[Route('/adoption_statistics', name: 'app_adoption_statistics')]
    public function adoptionStatistics(AdoptionRepository $repo): Response
{
    $pendingAdoptions = $repo->findBy(['Adoption_Status' => 'Pending']);
    $totalAdoptions = $repo->count([]);
    $adoptedAdoptions = $repo->count(['Adoption_Status' => 'Adopted']);
    $cancelledAdoptions = $repo->count(['Adoption_Status' => 'Cancelled']);

    return $this->render('/Front/Animal/ListAd.html.twig', [
        'result' => $result,
        'totalAdoptions' => $totalAdoptions,
        'pendingAdoptions' => $pendingAdoptions,
        'adoptedAdoptions' => $adoptedAdoptions,
        'cancelledAdoptions' => $cancelledAdoptions,
    ]);
}

/*
    #[Route('/update_animal_status', name: 'app_animal_status')]
    private function updateAnimalStatus(Adoption $adoption, Animal $animal): void
    {
        $currentDate = new DateTime();
        if ($adoption->getAdoptionDate() > $currentDate) {
            $animal->setAnimalStatus('Adopted');
        } else {
            $animal->setAnimalStatus('Pending');
        }
    }
*/

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

            $cashRegister = new CashRegister();
            $cashRegister->setType("Adoption");
            $cashRegister->setInput(1);
            $cashRegister->setOutput(0);


            $cashRegister->setSomme($s->getAdoptionFee());
            $cashRegister->setDateTransaction(new \DateTime());
            
            $cashRegister->setIdEntity($s->getAdoptionId());

            $em->persist($cashRegister);
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



    //////////////////////////front

    #[Route('List_adf{animalId}', name: 'app_listAdF')]
    public function AddAdF(ManagerRegistry $mr, Request $req, $animalId,SessionInterface $session): Response
    {
        $animal = $mr->getRepository(Animal::class)->find($animalId);
    
        $adoption = new Adoption();
    
      
        $adoption->setAdoptionStatus('Pending');
        $adoption->setAdoptionFee(200);
    
        
        $accountId = $session->get('user_id');
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


            $cashRegister = new CashRegister();
            $cashRegister->setType("Adoption");
            $cashRegister->setInput(1);
            $cashRegister->setOutput(0);


            $cashRegister->setSomme($adoption->getAdoptionFee());
            $cashRegister->setDateTransaction(new \DateTime());
            
            $cashRegister->setIdEntity($adoption->getAdoptionId());

            $em->persist($cashRegister);
            $em->flush();



            
    
            return $this->redirectToRoute('app_generate_qr_code_and_pdf',['adoptionId' => $adoption->getAdoptionId()]);
        } else {
            
            dump('Form errors:', $form->getErrors(true, false));
        }
    
        return $this->render('/Front/Animal/ListAd.html.twig', [
            'formAdoption' => $form->createView(),
            'user' => $user,
        ]);
    }
    


    // Symfony Controller
#[Route('/generate_qr_code_and_pdf{adoptionId}', name: 'app_generate_qr_code_and_pdf')]
public function generateAdoptionQrCodeAndPdf($adoptionId,SessionInterface $session): Response
{
        $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    $adoption = $this->getDoctrine()->getRepository(Adoption::class)->find($adoptionId);

    if (!$adoption) {
        throw $this->createNotFoundException('Adoption not found');
    }

    $adoptionData = [
        'adoptionId' => $adoption->getAdoptionId(),
        'adoptionDate' => $adoption->getAdoptionDate()->format('Y-m-d'),
        'adoptionStatus' => $adoption->getAdoptionStatus(),
        'adoptionFee' => $adoption->getAdoptionFee(),
        'accountName' => $adoption->getAccountKey()->getName(),
        'animalName' => $adoption->getAnimalKey()->getAnimalName(),
    ];

    // Debugging statement to inspect adoptionData
    dump($adoptionData);

    return $this->render('/Front/Animal/generate_qr_code.html.twig', [
        'adoptionData' => $adoptionData,
        'user' => $user,
    ]);
}


/*
#[Route('/generate_qr_code_and_pdf{adoptionId}', name: 'app_generate_qr_code_and_pdf')]
public function generateAdoptionQrCodeAndPdf($adoptionId, SessionInterface $session): Response
{
    $userId = $session->get('user_id');

        // Fetch user information from the database
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    // Fetch adoption details by ID
    $adoption = $this->getDoctrine()->getRepository(Adoption::class)->find($adoptionId);

    if (!$adoption) {
        throw $this->createNotFoundException('Adoption not found');
    }

    $writer = new PngWriter();
    $qrCode = QrCode::create(
        'Adoption ID: ' . $adoption->getAdoptionId() . PHP_EOL .
        'Date: ' . $adoption->getAdoptionDate()->format('Y-m-d') . PHP_EOL .
        'Status: ' . $adoption->getAdoptionStatus() . PHP_EOL .
        'Fee: ' . $adoption->getAdoptionFee() . PHP_EOL .
        'Account Name: ' . $adoption->getAccountKey()->getName() . PHP_EOL .
        'Animal Name: ' . $adoption->getAnimalKey()->getAnimalName()
    )
    ->setEncoding(new Encoding('UTF-8'))
    ->setSize(120)
    ->setMargin(0)
    ->setForegroundColor(new Color(0, 0, 0))
    ->setBackgroundColor(new Color(255, 255, 255));

    // Prepare QR codes
    $qrCodes = [];
    $qrCodes['img'] = $writer->write($qrCode)->getDataUri();
    $qrCodes['simple'] = $writer->write(
        $qrCode
    )->getDataUri();

    $qrCode->setForegroundColor(new Color(255, 0, 0));
    $qrCodes['changeColor'] = $writer->write(
        $qrCode
    )->getDataUri();

    $qrCode->setForegroundColor(new Color(0, 0, 0))->setBackgroundColor(new Color(255, 0, 0));
    $qrCodes['changeBgColor'] = $writer->write(
        $qrCode
    )->getDataUri();

    $qrCode->setSize(200)->setForegroundColor(new Color(0, 0, 0))->setBackgroundColor(new Color(255, 255, 255));
    $qrCodes['withImage'] = $writer->write(
        $qrCode
    )->getDataUri();

    return $this->render('/Front/Animal/generate_qr_code.html.twig', [
        'qrCodes' => $qrCodes,
        'user' => $user,
    ]);
}



*/


#[Route('/Hist_ad', name: 'app_HistAd')]
public function HistAd(AdoptionRepository $repo, SessionInterface $session): Response
{
    $userId = $session->get('user_id');
    $result = $repo->findBy(['Account_Key' => $userId]);
    $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);
    
    // Set the adoption ID in the session flash bag
    foreach ($result as $adoption) {
        $session->getFlashBag()->set('adoption_id', $adoption->getAdoptionId());
        break; // Set only the first adoption ID
    }

    // Render the template
    return $this->render('/Front/Animal/HistoryAd.html.twig', [
        'result' => $result,
        'user' => $user,
    ]);
}







#[Route('/update_adf{adoptionId}', name: 'app_update_Adf')]
public function updateAdf(ManagerRegistry $mr, Request $req, $adoptionId,SessionInterface $session): Response
{

    $userId = $session->get('user_id');
        $user = $this->getDoctrine()->getRepository(Account::class)->find($userId);

    $em = $mr->getManager();
    $adoption = $em->getRepository(Adoption::class)->find($adoptionId);

    $form = $this->createForm(AdoptionType::class, $adoption, ['is_admin' => false]);

    $form->handleRequest($req);

    if ($form->isSubmitted() && $form->isValid()) {
        $em = $mr->getManager();
        

        $em->flush();

        return $this->redirectToRoute('app_HistAd');
    }

    return $this->render('/Front/Animal/UpdateAd.html.twig', [
        'formAdoption' => $form->createView(),
        'adoptionId' => $adoptionId,
        'user' => $user,
    ]);
}



}





