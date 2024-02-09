<?php

namespace App\Entity;

use App\Repository\DonationMRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationMRepository::class)]
class DonationM
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?float $Donation_Amount = null;

    #[ORM\ManyToOne(inversedBy: 'DonationM_Key')]
    private ?CashRegister $cashRegister = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Donation $Donation_Key = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDonationAmount(): ?float
    {
        return $this->Donation_Amount;
    }

    public function setDonationAmount(float $Donation_Amount): static
    {
        $this->Donation_Amount = $Donation_Amount;

        return $this;
    }

    public function getCashRegister(): ?CashRegister
    {
        return $this->cashRegister;
    }

    public function setCashRegister(?CashRegister $cashRegister): static
    {
        $this->cashRegister = $cashRegister;

        return $this;
    }

    public function getDonationKey(): ?Donation
    {
        return $this->Donation_Key;
    }

    public function setDonationKey(?Donation $Donation_Key): static
    {
        $this->Donation_Key = $Donation_Key;

        return $this;
    }
}
