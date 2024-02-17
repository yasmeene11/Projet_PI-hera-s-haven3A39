<?php

namespace App\Entity;

use App\Repository\DonationMRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: DonationMRepository::class)]
class DonationM
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'donationMId')]
    private ?int $donationMId = null;

    #[ORM\Column]
    #[Assert\GreaterThanOrEqual(value: 10, message: "The donation amount should be equal to or greater than 10 DT.")]
    #[Assert\NotBlank( message: "You must enter the donation amount")]


    private ?float $Donation_Amount = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\EqualTo("today", message: "You must make a donation today")]
    #[Assert\NotBlank( message: "You must enter the donation date")]
    private ?\DateTimeInterface $donationM_Date = null;

    #[ORM\ManyToOne(inversedBy: 'donationMs')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    #[Assert\NotNull(message: "You must select an account")]
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
