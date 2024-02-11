<?php

namespace App\Entity;

use App\Repository\ProductRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ProductRepository::class)]
class Product
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'productId')]
    private ?int $productId = null;

    #[ORM\Column(length: 255)]
    private ?string $Product_Name = null;

    #[ORM\Column]
    private ?int $Product_Quantity = null;

    #[ORM\Column(length: 255)]
    private ?string $Product_Label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Expiration_Date = null;

    #[ORM\ManyToOne(inversedBy: 'products')]
    #[ORM\JoinColumn(nullable: false, name: 'Category_Key', referencedColumnName: 'categoryId')]
    private ?Category $Category_Key = null;

    #[ORM\OneToMany(mappedBy: 'Product_Key', targetEntity: DonationProduct::class)]
    private Collection $donationProducts;

    public function __construct()
    {
        $this->donationProducts = new ArrayCollection();
    }

   

   



   

   

  

    

    public function getproductId(): ?int
    {
        return $this->productId;
    }

    public function getProductName(): ?string
    {
        return $this->Product_Name;
    }

    public function setProductName(string $Product_Name): static
    {
        $this->Product_Name = $Product_Name;

        return $this;
    }

    public function getProductQuantity(): ?int
    {
        return $this->Product_Quantity;
    }

    public function setProductQuantity(int $Product_Quantity): static
    {
        $this->Product_Quantity = $Product_Quantity;

        return $this;
    }

    public function getProductLabel(): ?string
    {
        return $this->Product_Label;
    }

    public function setProductLabel(string $Product_Label): static
    {
        $this->Product_Label = $Product_Label;

        return $this;
    }

    public function getExpirationDate(): ?\DateTimeInterface
    {
        return $this->Expiration_Date;
    }

    public function setExpirationDate(\DateTimeInterface $Expiration_Date): static
    {
        $this->Expiration_Date = $Expiration_Date;

        return $this;
    }

    public function getCategoryKey(): ?Category
    {
        return $this->Category_Key;
    }

    public function setCategoryKey(?Category $Category_Key): static
    {
        $this->Category_Key = $Category_Key;

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
            $donationProduct->setProductKey($this);
        }

        return $this;
    }

    public function removeDonationProduct(DonationProduct $donationProduct): static
    {
        if ($this->donationProducts->removeElement($donationProduct)) {
            // set the owning side to null (unless already changed)
            if ($donationProduct->getProductKey() === $this) {
                $donationProduct->setProductKey(null);
            }
        }

        return $this;
    }

  

  

   

  

   

    
}
