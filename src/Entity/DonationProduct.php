<?php

namespace App\Entity;

use App\Repository\DonationProductRepository;
use Doctrine\ORM\Mapping as ORM;
#[ORM\Entity(repositoryClass: DonationProductRepository::class)]
class DonationProduct
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'dpId')]
    private ?int $dpId = null;

    #[ORM\ManyToOne(inversedBy: 'donationProducts')]
    #[ORM\JoinColumn(nullable: false, name: 'Donation_Key', referencedColumnName: 'donationPId')]
    private ?DonationP $Donation_Key = null;

    #[ORM\ManyToOne(inversedBy: 'donationProducts')]
    #[ORM\JoinColumn(nullable: false, name: 'Product_Key', referencedColumnName: 'productId')]
    private ?Product $Product_Key = null;

    public function getdpId(): ?int
    {
        return $this->dpId;
    }

    public function getDonationKey(): ?DonationP
    {
        return $this->Donation_Key;
    }

    public function setDonationKey(?DonationP $Donation_Key): static
    {
        $this->Donation_Key = $Donation_Key;

        return $this;
    }

    public function getProductKey(): ?Product
    {
        return $this->Product_Key;
    }

    public function setProductKey(?Product $Product_Key): static
    {
        $this->Product_Key = $Product_Key;

        return $this;
    }
    
    public function __toString()
    {
        return $this->Donation_Key;
    }
}