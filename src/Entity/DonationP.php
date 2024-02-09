<?php

namespace App\Entity;

use App\Repository\DonationPRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationPRepository::class)]
class DonationP
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Name = null;

    #[ORM\Column]
    private ?int $Donation_Product_Quantity = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Donation_Product_Expiration_Date = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Donation $Donation_Key = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDonationName(): ?string
    {
        return $this->Donation_Name;
    }

    public function setDonationName(string $Donation_Name): static
    {
        $this->Donation_Name = $Donation_Name;

        return $this;
    }

    public function getDonationProductQuantity(): ?int
    {
        return $this->Donation_Product_Quantity;
    }

    public function setDonationProductQuantity(int $Donation_Product_Quantity): static
    {
        $this->Donation_Product_Quantity = $Donation_Product_Quantity;

        return $this;
    }

    public function getDonationProductLabel(): ?string
    {
        return $this->Donation_Product_Label;
    }

    public function setDonationProductLabel(string $Donation_Product_Label): static
    {
        $this->Donation_Product_Label = $Donation_Product_Label;

        return $this;
    }

    public function getDonationProductExpirationDate(): ?\DateTimeInterface
    {
        return $this->Donation_Product_Expiration_Date;
    }

    public function setDonationProductExpirationDate(\DateTimeInterface $Donation_Product_Expiration_Date): static
    {
        $this->Donation_Product_Expiration_Date = $Donation_Product_Expiration_Date;

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
