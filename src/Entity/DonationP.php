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
    #[ORM\Column]
    private ?int $donationPId = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Name = null;

    #[ORM\Column]
    private ?int $Donation_Product_Quantity = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Product_Label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Donation_Product_Expiration_Date = null;

    #[ORM\ManyToMany(targetEntity: Product::class, mappedBy: 'DonationP_Key')]
    private Collection $products;

    public function __construct()
    {
        $this->products = new ArrayCollection();
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

    /**
     * @return Collection<int, Product>
     */
    public function getProducts(): Collection
    {
        return $this->products;
    }

    public function addProduct(Product $product): static
    {
        if (!$this->products->contains($product)) {
            $this->products->add($product);
            $product->addDonationPKey($this);
        }

        return $this;
    }

    public function removeProduct(Product $product): static
    {
        if ($this->products->removeElement($product)) {
            $product->removeDonationPKey($this);
        }

        return $this;
    }
}
