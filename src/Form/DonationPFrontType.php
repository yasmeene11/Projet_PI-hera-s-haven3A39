<?php

namespace App\Form;

use App\Entity\DonationP;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\Account;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class DonationPFrontType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('Donation_Product_Name', TextType::class, [
            'label' => 'Product Name',
        ])
        ->add('Donation_Product_Quantity', IntegerType::class, [
            'label' => 'Product Quantity',
            'required' => false,
        ])
        ->add('Donation_Product_Label', TextType::class, [
            'label' => 'Product Label',
        ])
        ->add('Donation_Product_Expiration_Date', DateType::class, [
            'label' => 'Product Expiration Date',
            'widget' => 'single_text',
            'required' => false,
        ])            
        ->add('DonationP_Date', DateType::class, [
            'label' => 'Donation Date',
            'widget' => 'single_text',
            'required' => false,
        ])    
        ->add('DonationP_Type', ChoiceType::class, [
            'label' => 'Product Type',
            'choices' => [
                'Medical' => 'medical',
                'Hygienic' => 'hygienic',
                'Food Supplies' => 'food_supplies',
            ],
            'required' => false,
            'placeholder' => 'Select product type', // optionnel, ajoutez ceci si vous voulez une option par défaut
        ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationP::class,
        ]);
    }
}
