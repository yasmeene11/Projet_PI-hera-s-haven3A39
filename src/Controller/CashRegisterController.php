<?php

namespace App\Controller;
use App\Entity\CashRegister;
use App\Repository\CashRegisterRepository;

//use App\Form\DonationMType;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Knp\Snappy\Pdf;
use DateTime;


class CashRegisterController extends AbstractController
{
    private $pdfService;

    public function __construct(Pdf $pdfService)
    {
        $this->pdfService = $pdfService;
    }

    #[Route('/cash/register', name: 'app_cash_register')]
    public function index(): Response
    {
        return $this->render('cash_register/index.html.twig', [
            'controller_name' => 'CashRegisterController',
        ]);
    }
    #[Route('/cashRegister', name: 'cashRegister')]
    public function listCashRegister(CashRegisterRepository $repo): Response
    {
        $result = $repo->findAll();
        $total = $repo->getTotal();
        return $this->render('/Back/CashRegister/listCashRegister.html.twig', [
            'result' => $result,
            'total'=> $total,
        ]);
    }
    #[Route('/statistics', name: 'app_statistics')]
    public function statisticts(CashRegisterRepository $cashRegisterRepository): Response
    {
    $statistics = $cashRegisterRepository->getStatisticsByType();

    return $this->render('/Back/CashRegister/statistics.html.twig', [
        'statistics' => $statistics,
    ]);
    }
   

     #[Route('/generate-pdf', name: 'generate-pdf', methods: ['POST'])]
    public function generatePdf(Request $request): Response
    {
        // Récupérer les dates de début et de fin envoyées par le formulaire
        $startDateString = $request->request->get('start_date');
    $endDateString = $request->request->get('end_date');

    // Convertir les chaînes de caractères en objets DateTimeInterface
    $startDate = new DateTime($startDateString);
    $endDate = new DateTime($endDateString);

        // Utiliser les dates pour récupérer les statistiques appropriées des CashRegister depuis la base de données
        $statisticsDonationM = $this->getDoctrine()->getRepository(CashRegister::class)->getStatisticsByDateRange('donationM', $startDate, $endDate);
        $statisticsBoarding = $this->getDoctrine()->getRepository(CashRegister::class)->getStatisticsByDateRange('boarding', $startDate, $endDate);
        $statisticsAdoption = $this->getDoctrine()->getRepository(CashRegister::class)->getStatisticsByDateRange('adoption', $startDate, $endDate);
        // Générer le PDF des statistiques
        // Vous devrez utiliser une bibliothèque pour générer des PDF comme Dompdf, Snappy ou TCPDF
        // Voici un exemple de code pour générer un PDF avec Dompdf
        $html = $this->renderView('/Back/CashRegister/statistics_pdf.html.twig', [
            'statisticsDonationM' => $statisticsDonationM,
            'statisticsBoarding' => $statisticsBoarding,
            'statisticsAdoption' => $statisticsAdoption,
            'startDate' => $startDate,
            'endDate' => $endDate,
        ]);

        $pdfContent = $this->pdfService->getOutputFromHtml($html);

        // Envoyer le PDF en réponse
        return new Response($pdfContent, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'inline; filename="statistics.pdf"',
        ]);
    }
}