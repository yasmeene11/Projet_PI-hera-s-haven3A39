<?php

namespace App\Entity;

use App\Repository\CashRegisterRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CashRegisterRepository::class)]
class CashRegister
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'cashRegisterId')]
    private ?int $cashRegisterId = null;

    #[ORM\Column]
    private ?float $Balance = null;

    #[ORM\Column]
    private ?float $Expenses = null;

    #[ORM\Column]
    private ?float $Donation_Total = null;

    #[ORM\Column]
    private ?float $Adoption_Fee_Total = null;

    #[ORM\Column]
    private ?float $Boarding_Fee_Total = null;

    public function getcashRegisterId(): ?int
    {
        return $this->cashRegisterId;
    }

    public function getBalance(): ?float
    {
        return $this->Balance;
    }

    public function setBalance(float $Balance): static
    {
        $this->Balance = $Balance;

        return $this;
    }

    public function getExpenses(): ?float
    {
        return $this->Expenses;
    }

    public function setExpenses(float $Expenses): static
    {
        $this->Expenses = $Expenses;

        return $this;
    }

    public function getDonationTotal(): ?float
    {
        return $this->Donation_Total;
    }

    public function setDonationTotal(float $Donation_Total): static
    {
        $this->Donation_Total = $Donation_Total;

        return $this;
    }

    public function getAdoptionFeeTotal(): ?float
    {
        return $this->Adoption_Fee_Total;
    }

    public function setAdoptionFeeTotal(float $Adoption_Fee_Total): static
    {
        $this->Adoption_Fee_Total = $Adoption_Fee_Total;

        return $this;
    }

    public function getBoardingFeeTotal(): ?float
    {
        return $this->Boarding_Fee_Total;
    }

    public function setBoardingFeeTotal(float $Boarding_Fee_Total): static
    {
        $this->Boarding_Fee_Total = $Boarding_Fee_Total;

        return $this;
    }
}
