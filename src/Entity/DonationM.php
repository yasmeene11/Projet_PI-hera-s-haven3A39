<?php

namespace App\Entity;

use App\Repository\DonationMRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationMRepository::class)]
class DonationM
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'donationMId')]
    private ?int $donationMId = null;

    #[ORM\Column]
    private ?float $Donation_Amount = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $donationM_Date = null;

    #[ORM\ManyToOne(inversedBy: 'donationMs')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    public function getdonationMId(): ?int
    {
        return $this->donationMId;
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

    public function getDonationMDate(): ?\DateTimeInterface
    {
        return $this->donationM_Date;
    }

    public function setDonationMDate(\DateTimeInterface $donationM_Date): static
    {
        $this->donationM_Date = $donationM_Date;

        return $this;
    }

    public function getAccountKey(): ?Account
    {
        return $this->Account_Key;
    }

    public function setAccountKey(?Account $Account_Key): static
    {
        $this->Account_Key = $Account_Key;

        return $this;
    }
}
