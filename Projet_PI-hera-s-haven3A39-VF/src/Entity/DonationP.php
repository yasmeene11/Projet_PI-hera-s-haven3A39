<?php

namespace App\Entity;

use App\Repository\DonationPRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationPRepository::class)]
class DonationP
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'donationPId')]
    private ?int $donationPId = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Name = null;

    #[ORM\Column]
    private ?int $Donation_Product_Quantity = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Donation_Product_Expiration_Date = null;

    

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $DonationP_Date = null;

    #[ORM\ManyToOne(inversedBy: 'donationPs')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    

    #[ORM\Column(length: 255)]
    private ?string $DonationP_Type = null;

    #[ORM\OneToMany(mappedBy: 'Donation_Key', targetEntity: DonationProduct::class)]
    private Collection $donationProducts;

    public function __construct()
    {
        $this->donationProducts = new ArrayCollection();
    }

   

    

   

   
    public function getdonationPId(): ?int
    {
        return $this->donationPId;
    }

    public function getDonationProductName(): ?string
    {
        return $this->Donation_Product_Name;
    }

    public function setDonationProductName(string $Donation_Product_Name): static
    {
        $this->Donation_Product_Name = $Donation_Product_Name;

        return $this;
    }

    public function getDonationProductQuantity(): ?string
    {
        return $this->Donation_Product_Quantity;
    }

    public function setDonationProductQuantity(string $Donation_Product_Quantity): static
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

  

    public function getDonationPDate(): ?\DateTimeInterface
    {
        return $this->DonationP_Date;
    }

    public function setDonationPDate(\DateTimeInterface $DonationP_Date): static
    {
        $this->DonationP_Date = $DonationP_Date;

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

   
    public function getDonationPType(): ?string
    {
        return $this->DonationP_Type;
    }

    public function setDonationPType(string $DonationP_Type): static
    {
        $this->DonationP_Type = $DonationP_Type;

        return $this;
    }

    /**
     * @return Collection<int, DonationProduct>
     */
    public function getDonationProducts(): Collection
    {
        return $this->donationProducts;
    }

    public function addDonationProduct(DonationProduct $donationProduct): static
    {
        if (!$this->donationProducts->contains($donationProduct)) {
            $this->donationProducts->add($donationProduct);
            $donationProduct->setDonationKey($this);
        }

        return $this;
    }

    public function removeDonationProduct(DonationProduct $donationProduct): static
    {
        if ($this->donationProducts->removeElement($donationProduct)) {
            // set the owning side to null (unless already changed)
            if ($donationProduct->getDonationKey() === $this) {
                $donationProduct->setDonationKey(null);
            }
        }

        return $this;
    }

   

   

  
}
